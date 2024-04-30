package com.vitisvision.vitisvisionservice.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * UserRequest class is a DTO class for holding the user request details.
 */
@Data
@AllArgsConstructor
@Builder
public class UserRequest {

    /**
     * The first name of the user.
     */
    @NotBlank(message = "not.blank.first.name")
    @Size(min = 2, max = 20, message = "invalid.size.first.name")
    private String firstName;

    /**
     * The last name of the user.
     */
    @NotBlank(message = "not.blank.last.name")
    @Size(min = 2, max = 20, message = "invalid.size.last.name")
    private String lastName;

}
