package com.vitisvision.vitisvisionservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.exception.ResourceNotFoundException;
import com.vitisvision.vitisvisionservice.util.AdvisorUtils;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LogoutExceptionHandlerTest {

    private LogoutExceptionHandler handler;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AdvisorUtils advisorUtils;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        handler = new LogoutExceptionHandler(advisorUtils);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void shouldHandleResourceNotFoundException() throws IOException {
        // Given
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");
        when(advisorUtils.getLocalizedMessage("invalid.jwt")).thenReturn("Token not found");
        when(advisorUtils.getErrorDetailsString(exception)).thenReturn("Resource not found");

        // When
        handler.handleLogoutException(response, exception);

        // Then
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = new ObjectMapper().readValue(responseWriter.toString(), ApiResponse.class);
        assertNotNull(apiResponse);
        assertEquals(HttpStatus.BAD_REQUEST.value(), apiResponse.getStatusCode());
        assertEquals("Token not found", apiResponse.getErrors().get(0).getMessage());
    }

    @Test
    void shouldHandleJwtException() throws IOException {
        // Given
        JwtException exception = new JwtException("JWT token error");
        when(advisorUtils.getLocalizedMessage("invalid.jwt")).thenReturn("Invalid JWT token");
        when(advisorUtils.getErrorDetailsString(exception)).thenReturn("JWT token error");

        // When
        handler.handleLogoutException(response, exception);

        // Then
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = new ObjectMapper().readValue(responseWriter.toString(), ApiResponse.class);
        assertNotNull(apiResponse);
        assertEquals(HttpStatus.BAD_REQUEST.value(), apiResponse.getStatusCode());
        assertEquals("Invalid JWT token", apiResponse.getErrors().get(0).getMessage());
    }

    @Test
    void shouldHandleUnknownException() throws IOException {
        // Given
        Exception exception = new Exception("Unknown error");
        when(advisorUtils.getLocalizedMessage("logout.error")).thenReturn("An error occurred while processing the request");
        when(advisorUtils.getErrorDetailsString(exception)).thenReturn("Unknown error");

        // When
        handler.handleLogoutException(response, exception);

        // Then
        verify(response).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = new ObjectMapper().readValue(responseWriter.toString(), ApiResponse.class);
        assertNotNull(apiResponse);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), apiResponse.getStatusCode());
        assertEquals("An error occurred while processing the request", apiResponse.getErrors().get(0).getMessage());
    }
}
