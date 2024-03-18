package com.vitisvision.vitisvisionservice.exception;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import static com.vitisvision.vitisvisionservice.util.AdvisorUtils.createErrorResponseEntity;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        List<ApiError> errors = List.of(
                new ApiError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getClass().toString(),
                        e.getMessage(),
                        LocalDateTime.now().toString()
                )
        );
        return createErrorResponseEntity(errors, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ApiError> errors = ex.getAllErrors().stream()
                .map(err -> new ApiError(
                        HttpStatus.BAD_REQUEST,
                        err.getDefaultMessage(),
                        "Validation error",
                        LocalDateTime.now().toString()
                ))
                .toList();

        return createErrorResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

}
