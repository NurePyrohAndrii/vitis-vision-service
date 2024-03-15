package com.vitisvision.vitisvisionservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.exception.ApiError;
import com.vitisvision.vitisvisionservice.exception.ApiResponse;
import com.vitisvision.vitisvisionservice.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class LogoutExceptionHandler {
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
