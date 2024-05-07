package com.vitisvision.vitisvisionservice.domain.device.mapper;

import com.vitisvision.vitisvisionservice.domain.device.dto.DeviceRequest;
import com.vitisvision.vitisvisionservice.domain.device.entity.Device;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * DeviceRequestMapper class is used to map the device request to device entity.
 */
@Service
public class DeviceRequestMapper implements Function<DeviceRequest, Device> {

    @Override
    public Device apply(DeviceRequest deviceRequest) {
        return Device.builder()
                .name(deviceRequest.getName())
                .description(deviceRequest.getDescription())
                .installationDate(LocalDate.parse(deviceRequest.getInstallationDate()))
                .deviceType(deviceRequest.getDeviceType())
                .manufacturer(deviceRequest.getManufacturer())
                .isActive(false)
                .build();
    }

    public void update(DeviceRequest deviceRequest, Device updatedDevice) {
        updatedDevice.setName(deviceRequest.getName());
        updatedDevice.setDescription(deviceRequest.getDescription());
        updatedDevice.setInstallationDate(LocalDate.parse(deviceRequest.getInstallationDate()));
        updatedDevice.setDeviceType(deviceRequest.getDeviceType());
        updatedDevice.setManufacturer(deviceRequest.getManufacturer());
    }
}
