package com.vitisvision.vitisvisionservice.domain.staff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The response object for the staff.
 */
@Data
@Builder
@AllArgsConstructor
public class StaffResponse {

    /**
     * The id of the staff.
     */
    private Integer id;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The email of the user. Application insures that this is unique.
     */
    private String email;

    /**
     * The role of the user in the vineyard.
     */
    private String role;
}
