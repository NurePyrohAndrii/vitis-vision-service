package com.vitisvision.vitisvisionservice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResourceNotFoundException is thrown when a resource is already present in the database.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor for ResourceNotFoundException.
     * @param message the message to be displayed
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
