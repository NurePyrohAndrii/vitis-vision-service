package com.vitisvision.vitisvisionservice.domain.vinayard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * VineyardResponse class is a DTO class that represents the vineyard response for getting vineyard details.
 */
@Data
@Builder
@AllArgsConstructor
public class VineyardResponse {

    /**
     * <p>The id of the entity returned.</p>
     */
    private Integer id;

    /**
     * Represents name of the company that owns the vineyard.
     */
    private String companyName;

    /**
     * Represents the name of the vineyard. DBA stands for "doing business as".
     */
    private String dbaName;

    /**
     * Represents the street address of the vineyard.
     */
    private String streetAddress;

    /**
     * Represents the city of the vineyard.
     */
    private String city;

    /**
     * Represents the zip code of state of the vineyard.
     */
    private String zipCode;

    /**
     * Represents the phone number of the vineyard that can be used to contact the vineyard.
     */
    private String phoneNumber;

    /**
     * Represents the email of the vineyard that can be used to contact the vineyard.
     */
    private String email;

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
