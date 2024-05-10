package com.vitisvision.vitisvisionservice.domain.device.entity;

import com.vitisvision.vitisvisionservice.common.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

/**
 * Device entity class.
 * This class is used to represent the device entity in the database.
 * The device entity represents a device in a vineyard.
 */
@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Device extends BaseEntity {

    /**
     * Represents the name of the device.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Represents the description of the device.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Represents the serial number of the device.
     */
    @Column(nullable = false)
    private LocalDate installationDate;

    /**
     * Represents the device type.
     */
    @Column(nullable = false)
    private String deviceType;

    /**
     * Represents the manufacturer of the device.
     */
    @Column(nullable = false)
    private String manufacturer;

    /**
     * Represents the device status.
     */
    @Column(nullable = false)
    private boolean isActive;

    /**
     * Represents the device data.
     */
    @OneToMany(mappedBy = "device", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<DeviceData> deviceData;
}
