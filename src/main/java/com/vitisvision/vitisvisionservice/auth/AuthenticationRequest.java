package com.vitisvision.vitisvisionservice.auth;

import com.vitisvision.vitisvisionservice.security.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthenticationRequest class is a POJO class that holds the email and password
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /**
     * Email field is a string that holds the email of the user trying to authenticate

     */
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String email;

    /**
     * Password field is a string that holds the password of the user trying to authenticate
     */
    @ValidPassword
    private String password;
}