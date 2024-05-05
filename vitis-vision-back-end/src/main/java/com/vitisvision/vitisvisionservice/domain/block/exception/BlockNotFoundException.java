package com.vitisvision.vitisvisionservice.domain.block.exception;

import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;

/**
 * BlockNotFoundException class is used to represent the exception when a block is not found.
 */
public class BlockNotFoundException extends ResourceNotFoundException {

    /**
     * Constructor to create a new BlockNotFoundException object with the provided message.
     *
     * @param message the message to be shown when the exception is thrown
     */
    public BlockNotFoundException(String message) {
        super(message);
    }
}
