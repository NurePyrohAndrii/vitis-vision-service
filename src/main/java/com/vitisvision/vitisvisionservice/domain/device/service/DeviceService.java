package com.vitisvision.vitisvisionservice.domain.device.service;

import com.vitisvision.vitisvisionservice.domain.device.dto.DeviceRequest;
import com.vitisvision.vitisvisionservice.domain.device.dto.DeviceResponse;
import com.vitisvision.vitisvisionservice.domain.device.entity.Device;
import com.vitisvision.vitisvisionservice.domain.device.exception.DeviceNotFoundException;
import com.vitisvision.vitisvisionservice.domain.device.mapper.DeviceRequestMapper;
import com.vitisvision.vitisvisionservice.domain.device.mapper.DeviceResponseMapper;
import com.vitisvision.vitisvisionservice.domain.device.repository.DeviceRepository;
import com.vitisvision.vitisvisionservice.domain.vine.entity.Vine;
import com.vitisvision.vitisvisionservice.domain.vine.service.VineService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;

/**
 * DeviceService class is a service class for device operations.
 */
@Service
@RequiredArgsConstructor
public class DeviceService {

    /**
     * DeviceRepository object is used to access the device data.
     */
    private final DeviceRepository deviceRepository;

    /**
     * VineService object is used to access the vine operations.
     */
    private final VineService vineService;

    /**
     * DeviceRequestMapper object is used to map the device request to device entity.
     */
    private final DeviceRequestMapper deviceRequestMapper;

    /**
     * DeviceResponseMapper object is used to map the device entity to device response.
     */
    private final DeviceResponseMapper deviceResponseMapper;

    /**
     * Create a new device in a vine with the provided details
     *
     * @param deviceRequest the request object containing the device details
     * @param vineId        the vine id
     * @param principal     the principal object containing the user details
     * @return the response object containing the device details
     */
    @PreAuthorize("hasAuthority('device:write')")
    @Transactional
    public DeviceResponse createDevice(DeviceRequest deviceRequest, Integer vineId, Principal principal) {
        // Ensure the vine exists and the user has access to it
        Vine upgradedVine = vineService.ensureVineAccess(vineId, principal);

        // Ensure the device does not exist in the vine
        if (upgradedVine.getDevice() != null) {
            throw new DeviceNotFoundException("device.exists.error");
        }

        // Create a new device
        Device createdDevice = deviceRequestMapper.apply(deviceRequest);
        upgradedVine.setDevice(createdDevice);
        return deviceResponseMapper.apply(deviceRepository.save(createdDevice));
    }

    /**
     * Update the device details
     *
     * @param deviceRequest the request object containing the device details
     * @param vineId        the vine id
     * @param principal     the principal object containing the user details
     * @return the response object containing the device details
     */
    @PreAuthorize("hasAuthority('device:write')")
    @Transactional
    public DeviceResponse updateDevice(
            DeviceRequest deviceRequest, Integer vineId,
            Integer deviceId, Principal principal
    ) {
        // Ensure the vine exists and the user has access to it
        Map<Vine, Device> accessedDetails = ensureOperationAccess(vineId, deviceId, principal);
        Vine upgradedVine = accessedDetails.keySet().iterator().next();
        Device updatedDevice = accessedDetails.values().iterator().next();

        // Update the device details and save the device
        deviceRequestMapper.update(deviceRequest, updatedDevice);
        upgradedVine.setDevice(updatedDevice);
        return deviceResponseMapper.apply(deviceRepository.save(updatedDevice));
    }

    /**
     * Delete the device
     *
     * @param vineId    the vine id
     * @param deviceId  the device id
     * @param principal the principal object containing the user details
     */
    @PreAuthorize("hasAuthority('device:delete')")
    @Transactional
    public void deleteDevice(Integer vineId, Integer deviceId, Principal principal) {
        // Ensure the vine exists and the user has access to it
        Map<Vine, Device> accessedDetails = ensureOperationAccess(vineId, deviceId, principal);
        Vine upgradedVine = accessedDetails.keySet().iterator().next();
        Device deletedDevice = accessedDetails.values().iterator().next();

        // Delete the device
        deviceRepository.delete(deletedDevice);
        upgradedVine.setDevice(null);
    }

    /**
     * Get the device details
     *
     * @param vineId    the vine id
     * @param deviceId  the device id
     * @param principal the principal object containing the user details
     * @return the response object containing the device details
     */
    @PreAuthorize("hasAuthority('device:read')")
    @Transactional
    public DeviceResponse getDevice(Integer vineId, Integer deviceId, Principal principal) {
        // Ensure the vine exists and the user has access to it
        Map<Vine, Device> accessedDetails = ensureOperationAccess(vineId, deviceId, principal);
        // Return the device details
        return deviceResponseMapper.apply(accessedDetails.values().iterator().next());
    }

    /**
     * Ensure the vine and the device exist and the user has access to it. Also validate the device belongs to the vine.
     *
     * @param vineId    the vine id
     * @param deviceId  the device id
     * @param principal the principal object containing the user details
     * @return the vine and device details
     */
    private Map<Vine, Device> ensureOperationAccess(Integer vineId, Integer deviceId, Principal principal) {
        // Ensure the vine exists and the user has access to it
        Vine upgradedVine = vineService.ensureVineAccess(vineId, principal);

        // Get the device details
        Device operateddDevice = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("device.not.found.error"));

        // Ensure the device exists and belongs to the vine
        if (upgradedVine.getDevice() == null || !upgradedVine.getDevice().getId().equals(deviceId)) {
            throw new DeviceNotFoundException("device.not.found.error");
        }

        return Map.of(upgradedVine, operateddDevice);
    }
}

