package com.vitisvision.vitisvisionservice.controller.vineyard.device;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.domain.device.dto.DeviceActivateResponse;
import com.vitisvision.vitisvisionservice.domain.device.dto.DeviceRequest;
import com.vitisvision.vitisvisionservice.domain.device.dto.DeviceResponse;
import com.vitisvision.vitisvisionservice.domain.device.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

/**
 * DeviceController class is a controller class for device operations.
 */
@RestController
@RequestMapping("api/v1/vines/{vineId}/devices")
@RequiredArgsConstructor
@Tag(name = "Device", description = "Endpoint for device details management in a vineyard")
public class DeviceController {

    /**
     * DeviceService object is used to access the device operations.
     */
    private final DeviceService deviceService;

    /**
     * Create a new device in a vine with the provided details
     *
     * @param vineId       the vine id
     * @param deviceRequest the request object containing the device details
     * @param principal    the principal object containing the user details
     * @return the response object containing the device details
     */
    @Operation(
            summary = "Create a new device in a vine",
            description = "Create a new device in a vine with the provided details"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<DeviceResponse>> createDevice(
            @PathVariable Integer vineId,
            @RequestBody @Valid DeviceRequest deviceRequest,
            Principal principal
    ) {
        DeviceResponse response = deviceService.createDevice(deviceRequest, vineId, principal);
        return ResponseEntity.created(URI.create("ap1/v1/vines/" + vineId + "/devices/" + response.getId()))
                .body(ApiResponse.success(response, HttpStatus.CREATED.value()));
    }

        /**
     * Get the device details by id
     *
     * @param vineId    the vine id
     * @param principal the principal object containing the user details
     * @return the response object containing the device details
     */
    @Operation(
            summary = "Get the device details by id",
            description = "Get the device details by id in a vineyard with the provided details"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<DeviceResponse>> getDevice(
            @PathVariable Integer vineId,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(deviceService.getDevice(vineId, principal), HttpStatus.OK.value()));
    }

    /**
     * Update the device details
     *
     * @param vineId       the vine id
     * @param deviceId     the device id
     * @param deviceRequest the request object containing the device details
     * @param principal    the principal object containing the user details
     * @return the response object containing the device details
     */
    @Operation(
            summary = "Update the device details",
            description = "Update the device details in a vineyard with the provided details"
    )
    @PutMapping("/{deviceId}")
    public ResponseEntity<ApiResponse<DeviceResponse>> updateDevice(
            @PathVariable Integer vineId,
            @PathVariable Integer deviceId,
            @RequestBody @Valid DeviceRequest deviceRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(deviceService.updateDevice(deviceRequest, vineId, deviceId, principal), HttpStatus.OK.value()));
    }

    /**
     * Delete the device by id
     *
     * @param vineId    the vine id
     * @param deviceId  the device id
     * @param principal the principal object containing the user details
     * @return the response object containing the device details
     */
    @Operation(
            summary = "Delete the device by id",
            description = "Delete the device by id in a vineyard with the provided details"
    )
    @DeleteMapping("/{deviceId}")
    public ResponseEntity<ApiResponse<Void>> deleteDevice(
            @PathVariable Integer vineId,
            @PathVariable Integer deviceId,
            Principal principal
    ) {
        deviceService.deleteDevice(vineId, deviceId, principal);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }

    /**
     * Activate the device with the provided frequency
     *
     * @param vineId    the vine id
     * @param deviceId  the device id
     * @param frequency the frequency of data collection from sensors
     * @param principal the principal object containing the user details
     * @return the response object containing the device details
     */
    @Operation(
            summary = "Activate the device with the provided frequency",
            description = "Activate the device with the provided frequency in a vineyard with the provided details"
    )
    @PostMapping("{deviceId}/activate/{frequency}")
    public ResponseEntity<ApiResponse<DeviceActivateResponse>> activateDevice(
            @PathVariable Integer vineId,
            @PathVariable Integer deviceId,
            @PathVariable Integer frequency,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(deviceService.activateDevice(vineId, deviceId, frequency, principal), HttpStatus.OK.value()));
    }

    /**
     * Deactivate the device
     *
     * @param vineId    the vine id
     * @param deviceId  the device id
     * @param principal the principal object containing the user details
     * @return the response object containing the device details
     */
    @Operation(
            summary = "Deactivate the device",
            description = "Deactivate the device in a vineyard with the provided details"
    )
    @PostMapping("{deviceId}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateDevice(
            @PathVariable Integer vineId,
            @PathVariable Integer deviceId,
            Principal principal
    ) {
        deviceService.deactivateDevice(vineId, deviceId, principal);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }
}
