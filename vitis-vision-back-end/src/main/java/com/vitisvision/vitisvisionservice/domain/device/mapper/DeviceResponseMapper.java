package com.vitisvision.vitisvisionservice.domain.device.mapper;

import com.vitisvision.vitisvisionservice.domain.device.dto.DeviceResponse;
import com.vitisvision.vitisvisionservice.domain.device.entity.Device;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * DeviceResponseMapper class is used to map the device entity to device response.
 */
@Service
public class DeviceResponseMapper implements Function<Device, DeviceResponse> {

    @Override
    public DeviceResponse apply(Device device){
        LocalDateTime lastUpdatedAt = device.getLastUpdatedAt();
        return DeviceResponse.builder()
                .id(device.getId())
                .name(device.getName())
                .description(device.getDescription())
                .installationDate(device.getInstallationDate().toString())
                .deviceType(device.getDeviceType())
                .manufacturer(device.getManufacturer())
                .isActive(device.isActive())
                .lastUpdatedAt(lastUpdatedAt != null ? lastUpdatedAt.toString() : null)
                .lastUpdatedBy(device.getLastUpdatedBy())
                .createdBy(device.getCreatedBy())
                .createdAt(device.getCreatedAt().toString())
                .build();
    }
}
