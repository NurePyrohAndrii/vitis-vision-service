package com.vitisvision.vitisvisionservice.util;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Utility class for defining helper methods for the advisor classes.
 */
@Component
public class AdvisorUtils {

    /**
     * Create a ResponseEntity object with the given errors and status.
     *
     * @param errors List of ApiError objects.
     * @param status HttpStatus object.
     * @return ResponseEntity object.
     */
    public static ResponseEntity<ApiResponse<List<ApiError>>> createErrorResponseEntity(List<ApiError> errors, HttpStatus status) {
        return new ResponseEntity<>(
                ApiResponse.error(
                        errors,
                        status.value()
                ),
                status
        );
    }

    /**
     * Get the status code from the ResponseStatus annotation of the given exception class.
     *
     * @param e Exception class with ResponseStatus annotation to get the status code from.
     * @return HttpStatus object with the status code extracted from the ResponseStatus annotation.
     */
    public static HttpStatus getAnnotationResponseStatusCode(Class<? extends Exception> e) {
        return e.getAnnotation(ResponseStatus.class).code();
    }

}
