package com.vitisvision.vitisvisionservice.domain.vine.dto;

import com.vitisvision.vitisvisionservice.common.validation.ValidDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * VineRequest class is a DTO class for vine request.
 */
@Data
@AllArgsConstructor
@Builder
public class VineRequest {

    /**
     * The vine number in a row.
     */
    @Min(value = 0, message = "invalid.vine.number")
    @NotNull(message = "invalid.vine.number")
    private Integer vineNumber;

    /**
     * The row number in a block.
     */
    @Min(value = 0, message = "invalid.row.number")
    @NotNull(message = "invalid.row.number")
    private Integer rowNumber;

    /**
     * The variety of the vine.
     */
    @NotBlank(message = "not.blank.vine.variety")
    private String variety;

    /**
     * Count of boles in a vine.
     */
    @Min(value = 0, message = "invalid.boles.count")
    @NotNull(message = "invalid.boles.count")
    private Integer bolesCount;

    /**
     * The planting date of the vine.
     */
    @ValidDate(format = "yyyy-MM-dd", message = "invalid.date")
    private String plantingDate;

    /**
     * The formation type of the vine.
     */
    @NotBlank(message = "not.blank.formation.type")
    private String formationType;

}