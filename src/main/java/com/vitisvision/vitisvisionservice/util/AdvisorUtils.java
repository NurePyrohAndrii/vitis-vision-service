package com.vitisvision.vitisvisionservice.util;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    public static HttpStatus getAnnotationResponseStatusCode(Class<? extends Exception> e) {
        return e.getAnnotation(ResponseStatus.class).code();
    }

}
