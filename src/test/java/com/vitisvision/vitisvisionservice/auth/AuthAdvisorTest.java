package com.vitisvision.vitisvisionservice.auth;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.exception.DuplicateResourceException;
import com.vitisvision.vitisvisionservice.util.AdvisorUtils;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
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
    private MessageSource messageSource;

    private AuthAdvisor authAdvisor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        AdvisorUtils advisorUtils = new AdvisorUtils(messageSource);
        authAdvisor = new AuthAdvisor(advisorUtils);
        when(messageSource.getMessage(any(), any(), eq(Locale.getDefault()))).thenReturn("Translated message");
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
