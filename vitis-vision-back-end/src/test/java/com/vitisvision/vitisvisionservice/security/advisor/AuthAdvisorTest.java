package com.vitisvision.vitisvisionservice.security.advisor;

import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.exception.DuplicateResourceException;
import com.vitisvision.vitisvisionservice.common.util.MessageSourceUtils;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class AuthAdvisorTest {

    @Mock
    private MessageSourceUtils messageSourceUtils;

    private AuthAdvisor authAdvisor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        AdvisorUtils advisorUtils = new AdvisorUtils(messageSourceUtils);
        authAdvisor = new AuthAdvisor(advisorUtils);
        when(messageSourceUtils.getLocalizedMessage(any())).thenReturn("Translated message");
    }

    @Test
    public void handleDuplicateResourceException_ReturnsConflictResponse() {
        // Given
        DuplicateResourceException exception = new DuplicateResourceException("error.email.duplicate");

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = authAdvisor.handleDuplicateResourceException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        ApiResponse<List<ApiError>> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertEquals("Translated message", apiResponse.getErrors().get(0).getMessage());
    }

    @Test
    public void handleJwtException_ReturnsBadRequestResponse() {
        // Given
        JwtException exception = new JwtException("invalid.jwt");

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = authAdvisor.handleJwtException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiResponse<List<ApiError>> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertEquals("Translated message", apiResponse.getErrors().get(0).getMessage());
    }

    @Test
    public void handleAuthenticationException_ReturnsUnauthorizedResponse() {
        // Given
        AuthenticationException exception = new BadCredentialsException("error.current.password");

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = authAdvisor.handleAuthenticationException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        ApiResponse<List<ApiError>> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertEquals("Translated message", apiResponse.getErrors().get(0).getMessage());
    }
}
