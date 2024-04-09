package com.vitisvision.vitisvisionservice.security.advisor;

import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.exception.DuplicateResourceException;
import com.vitisvision.vitisvisionservice.controller.security.AuthController;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AuthAdvisor class is a controller advice class that handles exceptions thrown by the AuthController class.
 */
@ControllerAdvice(assignableTypes = AuthController.class)
@RequiredArgsConstructor
public class AuthAdvisor {

    /**
     * AdvisorUtils object that contains utility methods.
     */
    private final AdvisorUtils advisorUtils;

    /**
     * Handles DuplicateResourceException thrown by the AuthController class.
     *
     * @param e DuplicateResourceException object that was thrown by the AuthController class. It contains the error message.
     * @return ResponseEntity<ApiResponse<List<ApiError> object that contains the error message and the status code.
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleDuplicateResourceException(DuplicateResourceException e) {
        HttpStatus status = advisorUtils.getAnnotationResponseStatusCode(e.getClass());

        List<ApiError> errors = List.of(
                new ApiError(
                        status,
                        advisorUtils.getErrorMessageString(e),
                        advisorUtils.getErrorDetailsString(e),
                        LocalDateTime.now().toString()
                )
        );

        return advisorUtils.createErrorResponseEntity(errors, status);
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
                        advisorUtils.getErrorMessageString(e),
                        advisorUtils.getErrorDetailsString(e),
                        LocalDateTime.now().toString()
                )
        );

        return advisorUtils.createErrorResponseEntity(errors, status);
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
                        advisorUtils.getErrorMessageString(e),
                        advisorUtils.getErrorDetailsString(e),
                        LocalDateTime.now().toString()
                )
        );

        return advisorUtils.createErrorResponseEntity(errors, status);
    }
}
