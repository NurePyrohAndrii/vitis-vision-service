package com.vitisvision.vitisvisionservice.domain.vine.dto;

import com.vitisvision.vitisvisionservice.common.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * VineResponse class is a DTO class for vine response.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class VineResponse extends BaseResponse {

    /**
     * Represents the vine number.
     */
    private Integer vineNumber;

    /**
     * Represents the row number of the vine.
     */
    private Integer rowNumber;

    /**
     * Represents the variety of the vine.
     */
    private String variety;

    /**
     * Represents the count of boles in the vine.
     */
    private Integer bolesCount;

    /**
     * Represents the planting date of the vine.
     */
    private String plantingDate;

    /**
     * Represents the formation type of the vine.
     */
    private String formationType;

}
