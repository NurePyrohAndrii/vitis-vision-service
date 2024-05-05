package com.vitisvision.vitisvisionservice.security.advisor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
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

        when(advisorUtils.createErrorResponseEntity(exception, HttpStatus.UNAUTHORIZED))
                .thenAnswer(
                        invocation -> {
                            List<ApiError> errors = List.of(new ApiError(HttpStatus.UNAUTHORIZED, "JWT token is invalid", "JWT token is expired", LocalDateTime.now().toString()));
                            ApiResponse<List<ApiError>> apiResponse = ApiResponse.error(errors, HttpStatus.UNAUTHORIZED.value());
                            return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
                        }
                );

        // When
        jwtExceptionHandler.handleJwtException(response, exception);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

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
