package com.vitisvision.vitisvisionservice.jwt;

import com.vitisvision.vitisvisionservice.exception.ApiError;
import com.vitisvision.vitisvisionservice.exception.ApiResponse;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@Slf4j
public class JwtExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException e) {
        log.warn("JWT token is invalid");
        return new ResponseEntity<>(
                ApiResponse.error(
                        List.of(
                                new ApiError(
                                        HttpStatus.UNAUTHORIZED,
                                        "invalid token",
                                        e.getMessage(),
                                        LocalDateTime.now()
                                )
                        ),
                        HttpStatus.UNAUTHORIZED.value()
                ),
                HttpStatus.UNAUTHORIZED
        );
    }

}
