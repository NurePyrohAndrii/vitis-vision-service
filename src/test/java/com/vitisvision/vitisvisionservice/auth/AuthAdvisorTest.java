package com.vitisvision.vitisvisionservice.auth;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.exception.DuplicateResourceException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthAdvisorTest {

    private AuthAdvisor authAdvisor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authAdvisor = new AuthAdvisor();
    }

    @Test
    public void handleDuplicateResourceException_ReturnsBadRequestResponse() {
        // Given
        DuplicateResourceException exception = new DuplicateResourceException("Resource already exists");

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = authAdvisor.handleDuplicateResourceException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        ApiResponse<List<ApiError>> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertEquals("Resource already exists", apiResponse.getErrors().get(0).getMessage());
    }

    @Test
    public void handleJwtException_ReturnsUnauthorizedResponse() {
        // Given
        JwtException exception = new JwtException("Invalid JWT token");

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = authAdvisor.handleJwtException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiResponse<List<ApiError>> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertEquals("Invalid JWT token", apiResponse.getErrors().get(0).getMessage());
    }
}