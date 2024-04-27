package com.vitisvision.vitisvisionservice.domain.vine.exception;

import com.vitisvision.vitisvisionservice.common.exception.DuplicateResourceException;

/**
 * VineDuplicationException class is an exception class for vine duplication.
 */
public class VineDuplicationException extends DuplicateResourceException {
    /**
     * Constructor for DuplicateResourceException
     *
     * @param message the message to be displayed
     */
    public VineDuplicationException(String message) {
        super(message);
    }
}
