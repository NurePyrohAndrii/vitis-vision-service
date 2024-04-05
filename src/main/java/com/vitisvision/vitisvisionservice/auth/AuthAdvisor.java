package com.vitisvision.vitisvisionservice.auth;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.exception.DuplicateResourceException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import static com.vitisvision.vitisvisionservice.util.AdvisorUtils.createErrorResponseEntity;
import static com.vitisvision.vitisvisionservice.util.AdvisorUtils.getAnnotationResponseStatusCode;

/**
 * AuthAdvisor class is a controller advice class that handles exceptions thrown by the AuthController class.
 */
@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthAdvisor {

    /**
     * Handles DuplicateResourceException thrown by the AuthController class.
     *
     * @param e DuplicateResourceException object that was thrown by the AuthController class. It contains the error message.
     * @return ResponseEntity<ApiResponse<List<ApiError> object that contains the error message and the status code.
     */
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

    /**
     * Handles JwtException thrown by the AuthController class.
     *
     * @param e JwtException object that was thrown by the AuthController class. It contains the error message.
     * @return ResponseEntity<ApiResponse<List<ApiError> object that contains the error message and the status code.
     */
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

    /**
     * Handles AuthenticationException thrown by the AuthController class.
     *
     * @param e AuthenticationException object that was thrown by the AuthController class. It contains the error message.
     * @return ResponseEntity<ApiResponse<List<ApiError> object that contains the error message and the status code.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleAuthenticationException(AuthenticationException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
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
