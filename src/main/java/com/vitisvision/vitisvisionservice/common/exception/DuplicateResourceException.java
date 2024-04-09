package com.vitisvision.vitisvisionservice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ChangePasswordException is thrown when the user tries to change the password
 */
@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateResourceException extends RuntimeException {

    /**
     * Constructor for DuplicateResourceException
     * @param message the message to be displayed
     */
    public DuplicateResourceException(String message) {
        super(message);
    }
}