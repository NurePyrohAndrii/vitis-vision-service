package com.vitisvision.vitisvisionservice.user;

import com.vitisvision.vitisvisionservice.security.ValidPassword;
import lombok.Builder;
import lombok.Data;

/**
 * ChangePasswordRequest class is used to hold the request data for changing the password.
 */
@Data
@Builder
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
