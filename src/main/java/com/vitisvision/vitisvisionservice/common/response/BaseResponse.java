package com.vitisvision.vitisvisionservice.common.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * BaseResponse class is a DTO class for holding the common fields of the response.
 */
@Data
@SuperBuilder
public class BaseResponse {

    //TODO: Make all response classes extend this class
    /**
     * Represents the id of the entity.
     */
    private Integer id;

    /**
     * Represents the created at field of the entity. This field is used to store the date and time when the entity was created.
     */
    private String createdAt;

    /**
     * Represents the created by field of the entity. This field is used to store the user who created the entity.
     */
    private String createdBy;

    /**
     * Represents the last updated at field of the entity. This field is used to store the date and time when the entity was last updated.
     */
    private String lastUpdatedAt;

    /**
     * Represents the last updated by field of the entity. This field is used to store the user who last updated the entity.
     */
    private String lastUpdatedBy;

}
