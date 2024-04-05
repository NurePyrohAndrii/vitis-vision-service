package com.vitisvision.vitisvisionservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * LogoutExceptionHandler class is responsible for handling exceptions that may occur during logout process.
 */
@Component
public class LogoutExceptionHandler {

    /**
     * Handle logout exception and write the error response to the response object.
     *
     * @param response the response object to write the error response
     * @param e        the exception that occurred during logout process
     * @throws IOException the io exception that may occur while writing the response
     */
    public void handleLogoutException(HttpServletResponse response, Exception e) throws IOException {
        HttpStatus status;
        String message = "JWT token is invalid";

        if (e instanceof ResourceNotFoundException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof JwtException){
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "An error occurred while processing the request";
        }

        ApiError apiError = ApiError.builder()
                .status(status)
                .message(message)
                .details(e.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();

        ApiResponse<?> apiResponse = ApiResponse.error(
                List.of(apiError),
                status.value()
        );

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), apiResponse);
    }
}
