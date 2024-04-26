package com.vitisvision.vitisvisionservice.domain.group.exception;

import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;

/**
 * This class is used to represent the exception thrown when a group is not found in the database.
 */
public class GroupNotFoundException extends ResourceNotFoundException {

    /**
     * Constructor for the GroupNotFoundException class.
     *
     * @param message the message to be displayed when the exception is thrown
     */
    public GroupNotFoundException(String message) {
        super(message);
    }
}
