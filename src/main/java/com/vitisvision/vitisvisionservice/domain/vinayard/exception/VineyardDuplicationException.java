package com.vitisvision.vitisvisionservice.domain.vinayard.exception;

import com.vitisvision.vitisvisionservice.common.exception.DuplicateResourceException;

/**
 * Exception class for handling vineyard duplication
 */
public class VineyardDuplicationException extends DuplicateResourceException {

    /**
     * Constructor for DuplicateResourceException
     *
     * @param message the message to be displayed
     */
    public VineyardDuplicationException(String message) {
        super(message);
    }
}
