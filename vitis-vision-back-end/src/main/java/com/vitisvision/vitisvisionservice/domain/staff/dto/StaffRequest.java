package com.vitisvision.vitisvisionservice.domain.staff.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO class for staff request
 */
@Data
@Builder
@AllArgsConstructor
public class StaffRequest {

    /**
     * The id of the staff to be hired, fired or updated
     */
    @NotNull(message = "invalid.staff.id")
    @Min(value = 1, message = "invalid.staff.id")
    private Integer staffId;

    /**
     * The name of the staff role in the vineyard
     */
    private String vineyardRole;

}
