package com.vitisvision.vitisvisionservice.domain.group.exception;

/**
 * VinesGroupAssignmentException is a custom exception class for handling vine group assignment exceptions.
 */
public class VinesGroupAssignmentException extends RuntimeException {

    /**
     * Constructs a new VinesGroupAssignmentException with the specified detail message.
     *
     * @param message the detail message
     */
    public VinesGroupAssignmentException(String message) {
        super(message);
    }
}
