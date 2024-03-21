package com.vitisvision.vitisvisionservice.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JwtExceptionHandlerTest {

    private JwtExceptionHandler jwtExceptionHandler;

    @BeforeEach
    public void setUp() {
        jwtExceptionHandler = new JwtExceptionHandler();
    }

    @Test
    public void handleJwtException_ShouldSetErrorResponse() throws IOException {
        // Given
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception("JWT token is expired");

        // When
        jwtExceptionHandler.handleJwtException(response, exception);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertEquals("application/json", response.getContentType());

        ApiResponse<?> apiResponse = new ObjectMapper().readValue(response.getContentAsString(), ApiResponse.class);

        assertNotNull(apiResponse);
        assertEquals("error", apiResponse.getStatus());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), apiResponse.getStatusCode());
        assertNotNull(apiResponse.getErrors());
        assertEquals(1, apiResponse.getErrors().size());
        assertEquals("JWT token is invalid", apiResponse.getErrors().get(0).getMessage());
        assertEquals("JWT token is expired", apiResponse.getErrors().get(0).getDetails());
        assertNotNull(apiResponse.getErrors().get(0).getTimestamp());
    }

}