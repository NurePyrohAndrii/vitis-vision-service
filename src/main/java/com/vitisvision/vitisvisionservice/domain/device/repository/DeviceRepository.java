package com.vitisvision.vitisvisionservice.domain.device.repository;

import com.vitisvision.vitisvisionservice.domain.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * DeviceRepository interface is used to access the device data.
 */
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    /**
     * Find the device by name.
     *
     * @param name the name of the device
     * @return the device object
     */
    Optional<Device> findByName(String name);

    /**
     * Check if the device exists by name.
     *
     * @param name the name of the device
     * @return true if the device exists, false otherwise
     */
    boolean existsByName(String name);
}
