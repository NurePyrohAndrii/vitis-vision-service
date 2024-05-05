package com.vitisvision.vitisvisionservice.domain.block.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * BlockResponse class is a DTO class that represents the block response for getting block details.
 */
@Data
@Builder
@AllArgsConstructor
public class BlockResponse {

    /**
     * <p>The id of the entity returned.</p>
     */
    private Integer id;

    /**
     * The name of the block.
     */
    private String name;

    /**
     * Partitioning type represents the principle of partitioning vineyard into blocks.
     */
    private String partitioningType;

    /**
     * Represents the row spacing in centimeters.
     * Row spacing is the distance between the rows of vines in a vineyard.
     */
    private double rowSpacing;

    /**
     * Represents the vine spacing in centimeters.
     * Vine spacing is the distance between the vines in a row.
     */
    private double vineSpacing;

    /**
     * Represents the row orientation in the vineyard.
     * Row orientation is the direction of the rows in the vineyard.
     */
    private String rowOrientation;

    /**
     * Represents the trellis system type used in the block.
     * Trellis system is the structure that supports the vine.
     */
    private String trellisSystemType;

    /**
     * <p>The created at field of the entity. This field is used to store the date and time when the entity was created.</p>
     */
    private String createdAt;

    /**
     * <p>The last updated at field of the entity. This field is used to store the date and time when the entity was last updated.</p>
     */
    private String lastUpdatedAt;

    /**
     * <p>The created by field of the entity. This field is used to store the user who created the entity.</p>
     */
    private String createdBy;

    /**
     * <p>The last updated by field of the entity. This field is used to store the user who last updated the entity.</p>
     */
    private String lastUpdatedBy;

}
