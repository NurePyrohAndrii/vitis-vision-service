package com.vitisvision.vitisvisionservice.domain.device.mapper;

import com.vitisvision.vitisvisionservice.domain.device.dto.DeviceDataResponse;
import com.vitisvision.vitisvisionservice.domain.device.entity.DeviceData;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.function.Function;

/**
 * DeviceDataResponseMapper class is a mapper class for mapping DeviceDataResponse to DeviceData.
 */
@Service
public class DeviceDataResponseMapper implements Function<DeviceDataResponse, List<DeviceData>> {

    @Override
    public List<DeviceData> apply(DeviceDataResponse deviceDataResponse) {
        return deviceDataResponse.getData().stream()
                .map(data -> {
                    DeviceData deviceData = new DeviceData();
                    deviceData.setAirTemperature(data.getAirTemperature());
                    deviceData.setAirHumidity(data.getAirHumidity());
                    deviceData.setGndTemperature(data.getGndTemperature());
                    deviceData.setGndHumidity(data.getGndHumidity());
                    deviceData.setLux(data.getLux());
                    deviceData.setTimestamp(Instant.parse(data.getTimestamp()));
                    return deviceData;
                })
                .toList();
    }
}
