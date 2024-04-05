package com.vitisvision.vitisvisionservice.auth;

import com.vitisvision.vitisvisionservice.security.ValidPassword;
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
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;

    /**
     * The last name of the user that is registering.
     */
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    /**
     * The email of the user that is registering.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String email;

    /**
     * The password of the user that is registering.
     */
    @ValidPassword
    private String password;
}
