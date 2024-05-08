package com.vitisvision.vitisvisionservice.domain.device.repository;

import com.vitisvision.vitisvisionservice.domain.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * DeviceRepository interface is used to access the device data.
 */
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    /**
     * Find device ids by block id.
     *
     * @param blockId the id of the block
     * @return the list of device ids
     */
    @Query("SELECT v.device.id FROM Vine v WHERE v.block.id = :blockId")
    List<Integer> findDeviceIdsByBlockId(Integer blockId);
}
