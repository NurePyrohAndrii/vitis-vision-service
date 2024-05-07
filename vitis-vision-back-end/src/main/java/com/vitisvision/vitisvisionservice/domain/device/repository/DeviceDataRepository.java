package com.vitisvision.vitisvisionservice.domain.device.repository;

import com.vitisvision.vitisvisionservice.domain.device.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DeviceDataRepository interface is a repository interface for DeviceData entity.
 */
public interface DeviceDataRepository extends JpaRepository<DeviceData, Integer> {
}
