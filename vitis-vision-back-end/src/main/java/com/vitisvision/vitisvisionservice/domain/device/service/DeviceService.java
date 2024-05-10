package com.vitisvision.vitisvisionservice.domain.device.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.domain.device.dto.*;
import com.vitisvision.vitisvisionservice.domain.device.entity.Device;
import com.vitisvision.vitisvisionservice.domain.device.entity.DeviceData;
import com.vitisvision.vitisvisionservice.domain.device.exception.DeviceActivationException;
import com.vitisvision.vitisvisionservice.domain.device.exception.DeviceNotFoundException;
import com.vitisvision.vitisvisionservice.domain.device.mapper.DeviceDataResponseMapper;
import com.vitisvision.vitisvisionservice.domain.device.mapper.DeviceRequestMapper;
import com.vitisvision.vitisvisionservice.domain.device.mapper.DeviceResponseMapper;
import com.vitisvision.vitisvisionservice.domain.device.repository.DeviceDataRepository;
import com.vitisvision.vitisvisionservice.domain.device.repository.DeviceRepository;
import com.vitisvision.vitisvisionservice.domain.vine.entity.Vine;
import com.vitisvision.vitisvisionservice.domain.vine.service.VineService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * DeviceService class is a service class for device operations.
 */
@Service
@RequiredArgsConstructor
public class DeviceService {

    /**
     * The device ip address.
     */
    @Value("${device.ip-address}")
    private String deviceAddress;

    /**
     * The device activation endpoint.
     */
    @Value("${device.endpoint.activate}")
    private String deviceActivationEndpoint;

    /**
     * The device deactivation endpoint.
     */
    @Value("${device.endpoint.deactivate}")
    private String deviceDeactivationUrl;

    /**
     * DeviceRepository object is used to access the device data.
     */
    private final DeviceRepository deviceRepository;

    /**
     * DeviceDataRepository object is used to access the device data.
     */
    private final DeviceDataRepository deviceDataRepository;

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
     * DeviceDataResponseMapper object is used to map the device data response to device data.
     */
    private final DeviceDataResponseMapper deviceDataResponseMapper;

    /**
     * RestTemplate object is used to access the REST operations.
     */
    private final RestTemplate restTemplate;

    /**
     * ObjectMapper object is used to access the object operations.
     */
    private final ObjectMapper objectMapper;

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
     * Get the device details by vine
     *
     * @param vineId    the vine id
     * @param principal the principal object containing the user details
     * @return the response object containing the device details
     */
    @PreAuthorize("hasAuthority('device:read')")
    @Transactional
    public DeviceResponse getDevice(Integer vineId, Principal principal) {
        // Ensure the vine exists and the user has access to it
        Vine upgradedVine = vineService.ensureVineAccess(vineId, principal);

        Device device = upgradedVine.getDevice();

        if (device == null) {
            throw new DeviceNotFoundException("device.not.found.error");
        }

        // Return the device details
        return deviceResponseMapper.apply(device);
    }

    /**
     * Activate the device
     *
     * @param vineId    the vine id
     * @param deviceId  the device id
     * @param frequency the frequency of data collection from sensors
     * @param principal the principal object containing the user details
     * @return the response object containing the device details
     */
    @PreAuthorize("hasAuthority('device:activate')")
    @Transactional
    public DeviceActivateResponse activateDevice(Integer vineId, Integer deviceId, Integer frequency, Principal principal) {
        if (frequency < 1) {
            throw new DeviceActivationException("invalid.frequency.error");
        }

        // Ensure the vine exists and the user has access to it
        Map<Vine, Device> accessedDetails = ensureOperationAccess(vineId, deviceId, principal);

        Device device = accessedDetails.values().iterator().next();

        DeviceActivateRequest deviceActivateRequest = DeviceActivateRequest.builder()
                .frequency(frequency)
                .timestamp(Instant.now().toString())
                .build();

        ResponseEntity<DeviceActivateResponse> response;
        try {
            response = restTemplate.postForEntity(
                    deviceAddress + device.getId() + deviceActivationEndpoint,
                    objectMapper.writeValueAsString(deviceActivateRequest),
                    DeviceActivateResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new DeviceActivationException("activating.client.server.error");
        } catch (RestClientException | JsonProcessingException e) {
            throw new DeviceActivationException("error.activating.device");
        }

        // Activate the device and return the response
        device.setActive(true);
        deviceRepository.save(device);
        return response.getBody();
    }

    /**
     * Deactivate the device
     *
     * @param vineId    the vine id
     * @param deviceId  the device id
     * @param principal the principal object containing the user details
     */
    @PreAuthorize("hasAuthority('device:deactivate')")
    @Transactional
    public void deactivateDevice(Integer vineId, Integer deviceId, Principal principal) {
        // Ensure the vine exists and the user has access to it
        Map<Vine, Device> accessedDetails = ensureOperationAccess(vineId, deviceId, principal);
        Device device = accessedDetails.values().iterator().next();

        // Attempt to call the endpoint and directly get the DeviceDataResponse
        try {
            ResponseEntity<DeviceDataResponse> response = restTemplate.getForEntity(
                    deviceAddress + device.getId() + deviceDeactivationUrl,
                    DeviceDataResponse.class);

            // Check response status and handle the response body
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                DeviceDataResponse deviceDataResponse = response.getBody();

                // Save the device data
                List<DeviceData> deviceDataList = deviceDataResponseMapper.apply(deviceDataResponse);
                deviceDataList.forEach(deviceData -> deviceData.setDevice(device));
                deviceDataRepository.saveAll(deviceDataList);
            } else {
                throw new DeviceActivationException("error.deactivating.device");
            }

            // Deactivate the device
            device.setActive(false);
            deviceRepository.save(device);

        } catch (RestClientException e) {
            throw new DeviceActivationException("error.deactivating.device");
        }
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

