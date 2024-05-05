package com.vitisvision.vitisvisionservice.domain.group.exception;

import com.vitisvision.vitisvisionservice.common.exception.DuplicateResourceException;

/**
 * GroupDuplicationException class.
 * This class is used to represent the exception that occurs when a group is duplicated.
 */
public class GroupDuplicationException extends DuplicateResourceException {

    /**
     * Constructor for the GroupDuplicationException class.
     *
     * @param message the message to be displayed
     */
    public GroupDuplicationException(String message) {
        super(message);
    }
}
