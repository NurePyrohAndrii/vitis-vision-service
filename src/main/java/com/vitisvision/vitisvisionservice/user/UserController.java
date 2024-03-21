package com.vitisvision.vitisvisionservice.user;

import com.vitisvision.vitisvisionservice.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Endpoints for user management")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Change the password of the authenticated user",
            description = "Change the password of the authenticated user with the provided details"
    )
    @PatchMapping
    public ResponseEntity<ApiResponse<String>> changePassword(
            @RequestBody @Valid ChangePasswordRequest request, Principal principal
    ) {
        userService.changePassword(request, principal);
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", HttpStatus.OK.value()));
    }

}
