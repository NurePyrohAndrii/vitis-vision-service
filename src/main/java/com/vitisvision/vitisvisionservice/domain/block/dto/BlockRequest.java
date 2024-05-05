package com.vitisvision.vitisvisionservice.domain.block.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * BlockRequest class is a DTO class that represents the block request for creating a new block or updating an existing block in a vineyard.
 */
@Data
@Builder
@AllArgsConstructor
public class BlockRequest {

    /**
     * The name of the block.
     */
    @NotBlank(message = "not.blank.block.name")
    private String name;

    /**
     * Partitioning type represents the principle of partitioning vineyard into blocks.
     */
    @NotBlank(message = "not.blank.partitioning.type")
    private String partitioningType;

    /**
     * Represents the row spacing in centimeters.
     * Row spacing is the distance between the rows of vines in a vineyard.
     */
    @DecimalMin(value = "0.0", message = "invalid.block.row.spacing")
    @DecimalMax(value = "1000.0", message = "invalid.block.row.spacing")
    @Positive(message = "invalid.block.row.spacing")
    @NotNull(message = "invalid.block.row.spacing")
    private double rowSpacing;

    /**
     * Represents the vine spacing in centimeters.
     * Vine spacing is the distance between the vines in a row.
     */
    @DecimalMin(value = "0.0", message = "invalid.row.vine.spacing")
    @DecimalMax(value = "1000.0", message = "invalid.row.vine.spacing")
    @Positive(message = "invalid.row.vine.spacing")
    @NotNull(message = "invalid.row.vine.spacing")
    private double vineSpacing;

    /**
     * Represents the row orientation in the vineyard.
     * Row orientation is the direction of the rows in the vineyard.
     */
    @NotBlank(message = "not.blank.row.orientation")
    private String rowOrientation;

    /**
     * Represents the trellis system type used in the block.
     * Trellis system is the structure that supports the vine.
     */
    @NotBlank(message = "not.blank.trellis.system.type")
    private String trellisSystemType;

}
