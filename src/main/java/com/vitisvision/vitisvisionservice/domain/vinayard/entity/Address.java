package com.vitisvision.vitisvisionservice.domain.vinayard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Address embeddable class.
 * This class is used to represent the address entity in the database.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    /**
     * Represents the street address of the vineyard.
     */
    @Column(nullable = false)
    private String streetAddress;

    /**
     * Represents the city of the vineyard.
     */
    @Column(nullable = false)
    private String city;

    /**
     * Represents the zip code of state of the vineyard.
     */
    @Column(nullable = false)
    private String zipCode;

}
