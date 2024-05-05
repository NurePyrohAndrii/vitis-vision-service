package com.vitisvision.vitisvisionservice.domain.vinayard.exception;

import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;

/**
 * VineyardNotFoundException is thrown when a vineyard is not found in the database.
 */
public class VineyardNotFoundException extends ResourceNotFoundException {

    /**
     * Constructor for VineyardNotFoundException.
     *
     * @param message the message to be displayed
     */
    public VineyardNotFoundException(String message) {
        super(message);
    }
}
