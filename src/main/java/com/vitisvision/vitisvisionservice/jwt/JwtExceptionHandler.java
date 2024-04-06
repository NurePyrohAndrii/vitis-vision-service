package com.vitisvision.vitisvisionservice.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.util.AdvisorUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class JwtExceptionHandler {

    /**
     * The AdvisorUtils object to handle advisor operations
     */
    private final AdvisorUtils advisorUtils;

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
                .message(advisorUtils.getErrorMessageString(e))
                .details(advisorUtils.getErrorDetailsString(e))
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
