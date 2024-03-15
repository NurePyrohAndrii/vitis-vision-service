package com.vitisvision.vitisvisionservice.util;

import com.vitisvision.vitisvisionservice.exception.ApiError;
import com.vitisvision.vitisvisionservice.exception.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdvisorUtils {

    public static ResponseEntity<ApiResponse<List<ApiError>>> createErrorResponseEntity(List<ApiError> errors, HttpStatus status) {
        return new ResponseEntity<>(
                ApiResponse.error(
                        errors,
                        status.value()
                ),
                status
        );
    }

}
