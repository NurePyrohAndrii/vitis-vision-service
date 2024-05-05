package com.vitisvision.vitisvisionservice.domain.device.exception;

import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;

/**
 * DeviceNotFoundException class is used to represent the exception when the device is not found.
 */
public class DeviceNotFoundException extends ResourceNotFoundException {

    /**
     * Constructs a new DeviceNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public DeviceNotFoundException(String message) {
        super(message);
    }
}
