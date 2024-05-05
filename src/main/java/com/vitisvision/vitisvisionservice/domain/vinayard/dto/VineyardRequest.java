package com.vitisvision.vitisvisionservice.domain.vinayard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * VineyardRequest class is a DTO class that represents the vineyard request for creating and updating vineyard.
 */
@Data
@Builder
@AllArgsConstructor
public class VineyardRequest {

    /**
     * Represents name of the company that owns the vineyard.
     */
    @NotBlank(message = "not.blank.company.name")
    private String companyName;

    /**
     * Represents the name of the vineyard. DBA stands for "doing business as".
     */
    @NotBlank(message = "not.blank.dba.name")
    private String dbaName;

    /**
     * Represents the street address of the vineyard.
     */
    @NotBlank(message = "not.blank.street.address")
    private String streetAddress;

    /**
     * Represents the city of the vineyard.
     */
    @NotBlank(message = "not.blank.city")
    private String city;

    /**
     * Represents the zip code of state of the vineyard.
     */
    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "invalid.zip.code")
    private String zipCode;

    /**
     * Represents the phone number of the vineyard that can be used to contact the vineyard.
     */
    @NotBlank(message = "not.blank.phone.number")
    @Pattern(regexp = "^[0-9]{10}$", message = "invalid.phone.number")
    private String phoneNumber;

    /**
     * Represents the email of the vineyard that can be used to contact the vineyard.
     */
    @NotBlank(message = "not.blank.email")
    @Email(message = "not.valid.email")
    private String email;

}
