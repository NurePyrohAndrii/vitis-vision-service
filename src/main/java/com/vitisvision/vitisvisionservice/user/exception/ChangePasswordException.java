package com.vitisvision.vitisvisionservice.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ChangePasswordException is a custom exception class for handling change password related exceptions.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ChangePasswordException extends RuntimeException {

    /**
     * Constructor to create an instance of ChangePasswordException.
     * @param message the message to be displayed when the exception is thrown.
     */
    public ChangePasswordException(String message) {
        super(message);
    }
}
