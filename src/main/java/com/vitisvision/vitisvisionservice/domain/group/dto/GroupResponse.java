package com.vitisvision.vitisvisionservice.domain.group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Group response class. Represents the response object group operations.
 */
@Data
@Builder
@AllArgsConstructor
public class GroupResponse {

    /**
     * The id of the group entity.
     */
    private Integer id;

    /**
     * Represents the name of the group.
     */
    private String name;

    /**
     * Represents the description of the group.
     */
    private String description;

    /**
     * Represents the reason for the formation of the group.
     */
    private String formationReason;

    /**
     * <p>The created at field of the entity.</p>
     */
    private String createdAt;

    /**
     * <p>The last updated at field of the entity. </p>
     */
    private String lastUpdatedAt;

    /**
     * <p>The created by field of the entity.</p>
     */
    private String createdBy;

    /**
     * <p>The last updated by field of the entity.</p>
     */
    private String lastUpdatedBy;

}
