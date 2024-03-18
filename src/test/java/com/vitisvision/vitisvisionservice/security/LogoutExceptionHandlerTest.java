package com.vitisvision.vitisvisionservice.security;

import static org.mockito.Mockito.*;

import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogoutExceptionHandlerTest {

    private LogoutExceptionHandler handler;
    private HttpServletResponse response;
    private StringWriter responseWriter;

    @BeforeEach
    void setUp() {
        handler = new LogoutExceptionHandler();
        response = mock(HttpServletResponse.class);
        responseWriter = new StringWriter();
        try {
            when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldHandleResourceNotFoundException() throws IOException {
        // Given
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        // When
        handler.handleLogoutException(response, exception);

        // Then
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        ApiResponse<?> apiResponse = new ObjectMapper().readValue(responseWriter.toString(), ApiResponse.class);
        assertEquals("error", apiResponse.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.value(), apiResponse.getStatusCode());
        assertEquals("JWT token is invalid", apiResponse.getErrors().get(0).getMessage());
    }

    @Test
    void shouldHandleJwtException() throws IOException {
        // Given
        JwtException exception = new JwtException("JWT token error");

        // When
        handler.handleLogoutException(response, exception);

        // Then
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        ApiResponse<?> apiResponse = new ObjectMapper().readValue(responseWriter.toString(), ApiResponse.class);
        assertEquals("error", apiResponse.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.value(), apiResponse.getStatusCode());
        assertEquals("JWT token is invalid", apiResponse.getErrors().get(0).getMessage());
    }

    @Test
    void shouldHandleUnknownException() throws IOException {
        // Given
        Exception exception = new Exception("Unknown error");

        // When
        handler.handleLogoutException(response, exception);

        // Then
        verify(response).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = new ObjectMapper().readValue(responseWriter.toString(), ApiResponse.class);
        assertEquals("error", apiResponse.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), apiResponse.getStatusCode());
        assertEquals("An error occurred while processing the request", apiResponse.getErrors().get(0).getMessage());
    }
}