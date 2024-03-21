package com.vitisvision.vitisvisionservice.user;

import com.vitisvision.vitisvisionservice.security.ValidPassword;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {
    @ValidPassword
    private String currentPassword;

    @ValidPassword
    private String newPassword;

    @ValidPassword
    private String confirmPassword;
}
