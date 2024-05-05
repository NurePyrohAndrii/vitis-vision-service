package com.vitisvision.vitisvisionservice.security.dto;

import com.vitisvision.vitisvisionservice.security.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RegisterRequest class is a POJO class that represents the request body of the register endpoint.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /**
     * The first name of the user that is registering.
     */
    @NotBlank(message = "not.blank.first.name")
    @Size(min = 2, max = 20, message = "invalid.size.first.name")
    private String firstName;

    /**
     * The last name of the user that is registering.
     */
    @NotBlank(message = "not.blank.last.name")
    @Size(min = 2, max = 20, message = "invalid.size.last.name")
    private String lastName;

    /**
     * The email of the user that is registering.
     */
    @NotBlank(message = "not.blank.email")
    @Email(message = "not.valid.email")
    private String email;

    /**
     * The password of the user that is registering.
     */
    @ValidPassword
    private String password;
}
