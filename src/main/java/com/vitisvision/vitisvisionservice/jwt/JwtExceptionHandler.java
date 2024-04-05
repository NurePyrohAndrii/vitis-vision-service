package com.vitisvision.vitisvisionservice.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * JwtExceptionHandler class for handling exceptions occurred in {@link JwtAuthenticationFilter}
 * <p>
 * This class is responsible for handling JWT exceptions.
 * </p>
 */
@Component
public class JwtExceptionHandler {

    /**
     * Handle exceptions occurred in {@link JwtAuthenticationFilter}
     *
     * @param response the response
     * @param e        the exception
     * @throws IOException the io exception occurred while writing response
     */
    public void handleJwtException(HttpServletResponse response, Exception e) throws IOException {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("JWT token is invalid")
                .details(e.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();

        ApiResponse<?> apiResponse = ApiResponse.error(
                List.of(apiError),
                HttpStatus.UNAUTHORIZED.value()
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), apiResponse);
    }

}
