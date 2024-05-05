package com.vitisvision.vitisvisionservice.user.dto;

import com.vitisvision.vitisvisionservice.security.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * ChangePasswordRequest class is used to hold the request data for changing the password.
 */
@Data
@Builder
@AllArgsConstructor
public class ChangePasswordRequest {

    /**
     * The current password of the user.
     */
    @ValidPassword
    private String currentPassword;

    /**
     * The new password of the user.
     */
    @ValidPassword
    private String newPassword;

    /**
     * The confirmation password of the user.
     */
    @ValidPassword
    private String confirmPassword;
}
