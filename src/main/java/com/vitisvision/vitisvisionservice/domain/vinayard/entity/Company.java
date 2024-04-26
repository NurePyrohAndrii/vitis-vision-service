package com.vitisvision.vitisvisionservice.domain.vinayard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Company embeddable class.
 * Represents the company that owns the vineyard.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Company {

    /**
     * Represents name of the company that owns the vineyard.
     */
    @Column(nullable = false)
    private String companyName;

    /**
     * Represents the name of the vineyard. DBA stands for "doing business as".
     */
    @Column(nullable = false, unique = true)
    private String dbaName;

    /**
     * Represents the address of the vineyard.
     */
    @Embedded
    private Address address;

    /**
     * Represents the phone number of the vineyard that can be used to contact the vineyard.
     */
    @Column(nullable = false)
    private String phoneNumber;

    /**
     * Represents the email of the vineyard that can be used to contact the vineyard.
     */
    @Column(nullable = false)
    private String email;

}
