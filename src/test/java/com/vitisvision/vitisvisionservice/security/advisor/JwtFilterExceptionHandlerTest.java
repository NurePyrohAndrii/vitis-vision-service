package com.vitisvision.vitisvisionservice.security.advisor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.security.advisor.JwtFilterExceptionHandler;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class JwtFilterExceptionHandlerTest {

    @Mock
    private AdvisorUtils advisorUtils;

    private JwtFilterExceptionHandler jwtExceptionHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtExceptionHandler = new JwtFilterExceptionHandler(advisorUtils);
    }

    @Test
    public void handleJwtException_ShouldSetErrorResponse() throws IOException {
        // Given
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception("JWT token is expired");
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("JWT token is invalid")
                .details("JWT token is expired")
                .timestamp(LocalDateTime.now().toString())
                .build();

        when(advisorUtils.getErrorMessageString(exception)).thenReturn("JWT token is invalid");
        when(advisorUtils.getErrorDetailsString(exception)).thenReturn("JWT token is expired");

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
