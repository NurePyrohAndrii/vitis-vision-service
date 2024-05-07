package com.vitisvision.vitisvisionservice.domain.device.advisor;

import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import com.vitisvision.vitisvisionservice.controller.vineyard.device.DeviceController;
import com.vitisvision.vitisvisionservice.domain.device.exception.DeviceActivationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * DeviceAdvisor class is an advisor class for device operations.
 */
@ControllerAdvice(assignableTypes = DeviceController.class)
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DeviceAdvisor {

    /**
     * AdvisorUtils object is used to access the advisor operations.
     */
    private final AdvisorUtils advisorUtils;

    /**
     * Handle the device activation exception
     *
     * @param e the device activation exception
     * @return the response entity containing the error details
     */
    @ExceptionHandler(DeviceActivationException.class)
    public ResponseEntity<ApiResponse<List<ApiError>>> handleDeviceActivationException(DeviceActivationException e) {
        return advisorUtils.createErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

}
