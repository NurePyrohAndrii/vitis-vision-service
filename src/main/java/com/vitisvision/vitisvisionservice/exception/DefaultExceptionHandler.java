package com.vitisvision.vitisvisionservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.warn("Exception occurred", e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                ApiResponse.error(
                        List.of(
                                new ApiError(
                                        HttpStatus.INTERNAL_SERVER_ERROR,
                                        e.getClass().toString(),
                                        e.getMessage(),
                                        LocalDateTime.now().toString()
                                )
                        ),
                        status.value()
                ),
                status
        );
    }

}
