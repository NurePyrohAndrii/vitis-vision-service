package com.vitisvision.vitisvisionservice.domain.device.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * DeviceData class is an entity class for device data handling.
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeviceData {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * The temperature value of the air.
     */
    @Column(nullable = false)
    private float airTemperature;

    /**
     * The humidity value of the air.
     */
    @Column(nullable = false)
    private float airHumidity;

    /**
     * The temperature value of the ground.
     */
    @Column(nullable = false)
    private float gndTemperature;

    /**
     * The humidity value of the ground.
     */
    @Column(nullable = false)
    private float gndHumidity;

    /**
     * The lux value of current environment.
     */
    @Column(nullable = false)
    private float lux;

    /**
     * The timestamp of the data.
     */
    @Column(nullable = false)
    private Instant timestamp;

    /**
     * The device that the data belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
}
