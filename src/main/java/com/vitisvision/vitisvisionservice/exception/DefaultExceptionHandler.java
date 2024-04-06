package com.vitisvision.vitisvisionservice.exception;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.util.AdvisorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
