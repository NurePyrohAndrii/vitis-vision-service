package com.vitisvision.vitisvisionservice.common.util;

import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Utility class for defining helper methods for the advisor classes.
 */
@Service
@RequiredArgsConstructor
public class AdvisorUtils {

    /**
     * MessageSource object for internationalization.
     */
    private final MessageSource messageSource;

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
        try {
            String exceptionFormat = messageSource.getMessage(
                    "error.advisor.exceptionFormat", null, LocaleContextHolder.getLocale());
            return String.format(exceptionFormat, e.getClass().getSimpleName());
        } catch (NoSuchMessageException ex) {
            return e.getClass().getSimpleName();
        }
    }

    /**
     * Get the internationalized error message from the given exception.
     *
     * @param e Exception object to get the error message from.
     * @return String object with the error message.
     */
    public String getErrorMessageString(Exception e) {
        try {
            return messageSource.getMessage(
                    e.getMessage(), null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            return e.getMessage();
        }
    }

    /**
     * Get the internationalized message from the given message key.
     *
     * @param message String object with the message key.
     * @return String object with the internationalized message.
     */
    public String getLocalizedMessage(String message) {
        try {
            return messageSource.getMessage(
                    message, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return message;
        }
    }
}
