package com.vitisvision.vitisvisionservice.domain.device.exception;

/**
 * DeviceActivationException class is an exception class for device activation.
 */
public class DeviceActivationException extends RuntimeException {

    /**
     * Constructor for DeviceActivationException.
     * @param message the message to be displayed
     */
    public DeviceActivationException(String message) {
        super(message);
    }
}
