package com.vitisvision.vitisvisionservice.auth;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.exception.DuplicateResourceException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

import static com.vitisvision.vitisvisionservice.util.AdvisorUtils.createErrorResponseEntity;
import static com.vitisvision.vitisvisionservice.util.AdvisorUtils.getAnnotationResponseStatusCode;

@ControllerAdvice(assignableTypes = AuthController.class)
@Slf4j
public class AuthAdvisor {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleDuplicateResourceException(DuplicateResourceException e) {
        HttpStatus status = getAnnotationResponseStatusCode(e.getClass());

        List<ApiError> errors = List.of(
                new ApiError(
                        status,
                        e.getMessage(),
                        "Exception " + e.getClass().getSimpleName() + " was thrown",
                        LocalDateTime.now().toString()
                )
        );

        return createErrorResponseEntity(errors, status);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleJwtException(JwtException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ApiError> errors = List.of(
                new ApiError(
                        status,
                        e.getMessage(),
                        "Exception " + e.getClass().getSimpleName() + " was thrown",
                        LocalDateTime.now().toString()
                )
        );

        return createErrorResponseEntity(errors, status);
    }
}
