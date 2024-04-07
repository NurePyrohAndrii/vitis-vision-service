package com.vitisvision.vitisvisionservice.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.common.api.ApiError;
import com.vitisvision.vitisvisionservice.common.api.ApiResponse;
import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;
import com.vitisvision.vitisvisionservice.util.AdvisorUtils;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class LogoutExceptionHandler {

    /**
     * The advisor utils to get the error message from the exception.
     */
    private final AdvisorUtils advisorUtils;

    /**
     * Handle logout exception and write the error response to the response object.
     *
     * @param response the response object to write the error response
     * @param e        the exception that occurred during logout process
     * @throws IOException the io exception that may occur while writing the response
     */
    public void handleLogoutException(HttpServletResponse response, Exception e) throws IOException {
        HttpStatus status;
        String message = "invalid.jwt";

        if (e instanceof ResourceNotFoundException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof JwtException){
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "logout.error";
        }

        ApiError apiError = ApiError.builder()
                .status(status)
                .message(advisorUtils.getLocalizedMessage(message))
                .details(advisorUtils.getErrorDetailsString(e))
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
