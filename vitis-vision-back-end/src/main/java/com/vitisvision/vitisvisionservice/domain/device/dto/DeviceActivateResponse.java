package com.vitisvision.vitisvisionservice.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeviceActivateResponse class is a response class for device activation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceActivateResponse {

    /**
     * Message about the device activation.
     */
    @JsonProperty("message")
    private String message;
}
