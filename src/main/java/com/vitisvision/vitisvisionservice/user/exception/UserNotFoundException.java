package com.vitisvision.vitisvisionservice.user.exception;

import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;

/**
 * UserNotFoundException class.
 * This class is used to represent the exception when a user is not found.
 */
public class UserNotFoundException extends ResourceNotFoundException {
    /**
     * Constructor for ResourceNotFoundException.
     *
     * @param message the message to be displayed
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
