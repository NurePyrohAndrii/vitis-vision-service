package com.vitisvision.vitisvisionservice.domain.block.dto;

import com.vitisvision.vitisvisionservice.common.validation.ValidDate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * BlockRequest class represents the request for creating a new report.
 */
@Data
@Builder
@AllArgsConstructor
public class BlockReportRequest {

    /**
     * The start date for the report. Represents the start date for the data aggregation.
     */
    @ValidDate(message = "invalid.date", format = "yyyy-MM-dd")
    private String startDate;

    /**
     * The end date for the report. Represents the end date for the data aggregation.
     */
    @ValidDate(message = "invalid.date", format = "yyyy-MM-dd")
    private String endDate;

    /**
     * The aggregation interval for the report. Represents the interval for the data aggregation in minutes.
     */
    @NotNull(message = "invalid.aggregation.interval")
    @Min(value = 1, message = "invalid.aggregation.interval")
    @Max(value = 1440, message = "invalid.aggregation.interval")
    private Integer aggregationInterval;
}
