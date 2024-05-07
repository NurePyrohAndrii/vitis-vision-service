package com.vitisvision.vitisvisionservice.domain.device.dto;

import com.vitisvision.vitisvisionservice.common.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * DeviceResponse class is a response class for device operations.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class DeviceResponse extends BaseResponse {

    /**
     * Represents the name of the device.
     */
    private String name;

    /**
     * Represents the serial number of the device.
     */
    private String installationDate;

    /**
     * Represents the description of the device.
     */
    private String description;

    /**
     * Represents the device type.
     */
    private String deviceType;

    /**
     * Represents the manufacturer of the device.
     */
    private String manufacturer;

    /**
     * Represents the device status.
     */
    private boolean isActive;

}
