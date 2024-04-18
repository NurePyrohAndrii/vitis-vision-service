package com.vitisvision.vitisvisionservice.common.advisor;

import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DefaultExceptionHandler class is a global exception handler for the application.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler {

    /**
     * The AdvisorUtils object that contains utility methods.
     */
    private final AdvisorUtils advisorUtils;

    /**
     * Handle all exceptions.
     *
     * @param e the exception object of type {@link Throwable}
     * @return the response entity
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        List<ApiError> errors = List.of(
                new ApiError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        advisorUtils.getErrorMessageString(e),
                        advisorUtils.getErrorDetailsString(e),
                        LocalDateTime.now().toString()
                )
        );
        return advisorUtils.createErrorResponseEntity(errors, status);
    }

    /**
     * Handle validation exception that occurs when a request body is not valid or has validation errors.
     *
     * @param ex the exception object of type {@link MethodArgumentNotValidException}
     * @return the response entity with the list of errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ApiError> errors = ex.getAllErrors().stream()
                .map(err -> new ApiError(
                        HttpStatus.BAD_REQUEST,
                        advisorUtils.getLocalizedMessage(err.getDefaultMessage()),
                        advisorUtils.getLocalizedMessage("validation.error"),
                        LocalDateTime.now().toString()
                ))
                .toList();

        return advisorUtils.createErrorResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle access denied exception that occurs when a user tries to access a resource without proper permissions.
     *
     * @param e the exception object of type {@link AccessDeniedException}
     * @return the response entity with the list of errors
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleAccessDeniedException(AccessDeniedException e) {
        List<ApiError> errors = List.of(
                new ApiError(
                        HttpStatus.FORBIDDEN,
                        advisorUtils.getLocalizedMessage("error.access.denied"),
                        advisorUtils.getErrorDetailsString(e),
                        LocalDateTime.now().toString()
                )
        );

        return advisorUtils.createErrorResponseEntity(errors, HttpStatus.FORBIDDEN);
    }
}
