package com.vitisvision.vitisvisionservice.domain.block.exception;

import com.vitisvision.vitisvisionservice.common.exception.DuplicateResourceException;

/**
 * Exception class for handling block duplication while creating a new block or updating an existing one.
 */
public class BlockDuplicationException extends DuplicateResourceException {

    /**
     * Constructor for DuplicateResourceException
     *
     * @param message the message to be displayed
     */
    public BlockDuplicationException(String message) {
        super(message);
    }
}
