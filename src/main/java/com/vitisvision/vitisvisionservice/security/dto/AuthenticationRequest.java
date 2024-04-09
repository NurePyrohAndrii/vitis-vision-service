package com.vitisvision.vitisvisionservice.security.dto;

import com.vitisvision.vitisvisionservice.security.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * AuthenticationRequest class is a POJO class that holds the email and password
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationRequest {

    /**
     * Email field is a string that holds the email of the user trying to authenticate

     */
    @NotBlank(message = "not.blank.email")
    @Email(message = "not.valid.email")
    private String email;

    /**
     * Password field is a string that holds the password of the user trying to authenticate
     */
    @ValidPassword
    private String password;
}