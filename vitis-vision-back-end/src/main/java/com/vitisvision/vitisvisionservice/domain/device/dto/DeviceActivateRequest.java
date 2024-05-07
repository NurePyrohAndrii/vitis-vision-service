package com.vitisvision.vitisvisionservice.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

/**
 * DeviceActivateRequest class is a request class for device activation.
 */
@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class DeviceActivateRequest {

    /**
     * Represents the frequency of data collection from sensors.
     */
    @JsonProperty("frequency")
    private Integer frequency;

    /**
     * Represents the timestamp of the device activation.
     */
    @JsonProperty("timestamp")
    private String timestamp;

}
