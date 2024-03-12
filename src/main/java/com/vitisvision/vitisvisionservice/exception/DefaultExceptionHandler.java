package com.vitisvision.vitisvisionservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(Exception e) {
        log.warn("JWT token is invalid");
        return new ResponseEntity<>(
                ApiResponse.error(
                        List.of(
                                new ApiError(
                                        HttpStatus.UNAUTHORIZED,
                                        "invalid token",
                                        e.getMessage(),
                                        LocalDateTime.now().toString()
                                )
                        ),
                        HttpStatus.UNAUTHORIZED.value()
                ),
                HttpStatus.UNAUTHORIZED
        );
    }

}
