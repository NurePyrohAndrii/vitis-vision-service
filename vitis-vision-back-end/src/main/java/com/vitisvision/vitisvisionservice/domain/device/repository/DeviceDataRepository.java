package com.vitisvision.vitisvisionservice.domain.device.repository;

import com.vitisvision.vitisvisionservice.domain.device.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

/**
 * DeviceDataRepository interface is a repository interface for DeviceData entity.
 */
public interface DeviceDataRepository extends JpaRepository<DeviceData, Integer> {

    /**
     * Find all device data by device ids and timestamp between.
     *
     * @param deviceIds      the list of device ids
     * @param startTimestamp the start timestamp
     * @param endTimestamp   the end timestamp
     * @return the list of device data
     */
    @Query("""
                SELECT d
                    FROM DeviceData d
                    WHERE d.device.id IN :deviceIds
                    AND d.timestamp BETWEEN :startTimestamp AND :endTimestamp
            """)
    List<DeviceData> findAllByDeviceIdsAndTimestampBetween(List<Integer> deviceIds, Instant startTimestamp, Instant endTimestamp);
}
