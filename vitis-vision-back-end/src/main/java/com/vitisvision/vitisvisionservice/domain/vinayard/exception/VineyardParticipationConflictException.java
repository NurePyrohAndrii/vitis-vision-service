package com.vitisvision.vitisvisionservice.domain.vinayard.exception;

/**
 * VineyardParticipationConflictException class.
 * This class is used to represent the exception when a vineyard participation conflict occurs.
 */
public class VineyardParticipationConflictException extends RuntimeException {

    /**
     * Constructor for VineyardParticipationConflictException.
     * @param message the message to be displayed
     */
    public VineyardParticipationConflictException(String message) {
        super(message);
    }
}
