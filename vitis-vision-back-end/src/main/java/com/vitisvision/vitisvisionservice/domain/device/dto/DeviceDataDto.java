package com.vitisvision.vitisvisionservice.domain.device.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeviceDataDto class is a DTO class for device data handling.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDataDto {

    /**
     * The temperature value of the air.
     */
    @JsonProperty("air_temperature")
    private float airTemperature;

    /**
     * The humidity value of the air.
     */
    @JsonProperty("air_humidity")
    private float airHumidity;

    /**
     * The temperature value of the ground.
     */
    @JsonProperty("gnd_temperature")
    private float gndTemperature;

    /**
     * The humidity value of the ground.
     */
    @JsonProperty("gnd_humidity")
    private float gndHumidity;

    /**
     * The lux value of current environment.
     */
    @JsonProperty("lux")
    private float lux;

    /**
     * The timestamp of the data.
     */
    @JsonProperty("timestamp")
    private String timestamp;
}
