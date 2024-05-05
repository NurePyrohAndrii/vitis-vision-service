package com.vitisvision.vitisvisionservice.common.util;

import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Utility class for defining helper methods for the advisor classes.
 */
@Service
@RequiredArgsConstructor
public class AdvisorUtils {

    /**
     * MessageSourceUtils object for using the message source.
     */
    private final MessageSourceUtils messageSourceUtils;


    /**
     * Create a ResponseEntity object with the given exception and status.
     *
     * @param e Exception object.
     * @param status HttpStatus object.
     * @return ResponseEntity object.
     */
    public ResponseEntity<ApiResponse<List<ApiError>>> createErrorResponseEntity(Exception e, HttpStatus status) {
        return createErrorResponseEntity(List.of(getApiErrorWithStatus(e, status)), status);
    }


    /**
     * Create a ResponseEntity object with the given errors and status.
     *
     * @param errors List of ApiError objects.
     * @param status HttpStatus object.
     * @return ResponseEntity object.
     */
    public ResponseEntity<ApiResponse<List<ApiError>>> createErrorResponseEntity(List<ApiError> errors, HttpStatus status) {
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
    public HttpStatus getAnnotationResponseStatusCode(Class<? extends Exception> e) {
        return e.getAnnotation(ResponseStatus.class).code();
    }

    /**
     * Get the internationalized error details string from the given exception.
     *
     * @param e Exception object to get the error details string from.
     * @return String object with the error details.
     */
    public String getErrorDetailsString(Exception e) {
        return String.format(messageSourceUtils.getLocalizedMessage("error.advisor.exceptionFormat"), e.getClass().getSimpleName());
    }


    /**
     * Get the ApiError object with the status, error message, error details, and timestamp from the given exception.
     *
     * @param e Exception object to get the error message and details from.
     * @param status HttpStatus object to set the status of the ApiError object.
     * @return ApiError object with the status, error message, error details, and timestamp.
     */
    private ApiError getApiErrorWithStatus(Exception e, HttpStatus status) {
        return new ApiError(
                status,
                getErrorMessageString(e),
                getErrorDetailsString(e),
                LocalDateTime.now().toString()
        );
    }

    /**
     * Get the internationalized error message from the given exception.
     *
     * @param e Exception object to get the error message from.
     * @return String object with the error message.
     */
    private String getErrorMessageString(Exception e) {
        return messageSourceUtils.getLocalizedMessage(e.getMessage());
    }
}
