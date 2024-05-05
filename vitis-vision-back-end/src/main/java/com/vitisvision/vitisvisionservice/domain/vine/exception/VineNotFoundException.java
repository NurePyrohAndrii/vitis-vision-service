package com.vitisvision.vitisvisionservice.domain.vine.exception;

import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;

/**
 * VineNotFoundException class is an exception class for vine not found errors.
 */
public class VineNotFoundException extends ResourceNotFoundException {

    /**
     * Constructs a new VineNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public VineNotFoundException(String message) {
        super(message);
    }
}
