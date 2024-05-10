package com.vitisvision.vitisvisionservice.domain.device.dto;

import com.vitisvision.vitisvisionservice.common.validation.ValidDate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DeviceRequest {

    /**
     * Represents the name of the device.
     */
    @NotBlank(message = "not.blank.device.name")
    private String name;

    /**
     * Represents the description of the device.
     */
    @NotBlank(message = "not.blank.device.description")
    private String description;

    /**
     * Represents the serial number of the device.
     */
    @ValidDate(format = "yyyy-MM-dd", message = "invalid.date")
    private String installationDate;

    /**
     * Represents the device type.
     */
    @NotBlank(message = "not.blank.device.type")
    private String deviceType;

    /**
     * Represents the manufacturer of the device.
     */
    @NotBlank(message = "not.blank.device.manufacturer")
    private String manufacturer;
}
