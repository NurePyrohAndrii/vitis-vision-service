package com.vitisvision.vitisvisionservice.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DeviceDataResponse class is a response class for device data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDataResponse {

    /**
     * The device data list of the device.
     */
    @JsonProperty("data")
    private List<DeviceDataDto> data;
}
