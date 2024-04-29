package com.vitisvision.vitisvisionservice.domain.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GroupVineResponse {

    /**
     * Represents the vine id.
     */
    private Integer vineId;

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

    /**
     * Represents the block id of the vine.
     */
    private String blockId;

}
