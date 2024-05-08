package com.vitisvision.vitisvisionservice.common.advisor;

import com.vitisvision.vitisvisionservice.common.exception.DuplicateResourceException;
import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;
import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import com.vitisvision.vitisvisionservice.common.util.MessageSourceUtils;
import lombok.RequiredArgsConstructor;
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
public class GlobalAdvisor {

    /**
     * The AdvisorUtils object that contains utility methods.
     */
    private final AdvisorUtils advisorUtils;

    /**
     * The MessageSourceUtils object that contains utility methods for message source.
     */
    private final MessageSourceUtils messageSourceUtils;

    /**
     * Handle all exceptions.
     *
     * @param e the exception object of type {@link Throwable}
     * @return the response entity
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleException(Exception e) {
        List<ApiError> errors = List.of(
                new ApiError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        messageSourceUtils.getLocalizedMessage("global.error"),
                        advisorUtils.getErrorDetailsString(e),
                        LocalDateTime.now().toString()
                )
        );

        return advisorUtils.createErrorResponseEntity(errors, HttpStatus.INTERNAL_SERVER_ERROR);
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
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        messageSourceUtils.getLocalizedMessage(err.getDefaultMessage()),
                        messageSourceUtils.getLocalizedMessage("validation.error"),
                        LocalDateTime.now().toString()
                ))
                .toList();

        return advisorUtils.createErrorResponseEntity(errors, HttpStatus.UNPROCESSABLE_ENTITY);
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
                        messageSourceUtils.getLocalizedMessage("error.access.denied"),
                        advisorUtils.getErrorDetailsString(e),
                        LocalDateTime.now().toString()
                )
        );

        return advisorUtils.createErrorResponseEntity(errors, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle resource not found exception that occurs when a resource is not found in the database.
     *
     * @param e the exception object of type {@link ResourceNotFoundException}
     * @return the response entity with the list of errors
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return advisorUtils.createErrorResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle duplicate resource exception that occurs when a resource is already present in the database.
     *
     * @param e the exception object of type {@link DuplicateResourceException}
     * @return the response entity with the list of errors
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleDuplicateResourceException(DuplicateResourceException e) {
        return advisorUtils.createErrorResponseEntity(e, HttpStatus.CONFLICT);
    }

    /**
     * Handle illegal argument exception that occurs when an illegal argument is passed to a method.
     *
     * @param e the exception object of type {@link IllegalArgumentException}
     * @return the response entity with the list of errors
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleIllegalArgumentException(IllegalArgumentException e) {
        return advisorUtils.createErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }
}
