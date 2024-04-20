package com.vitisvision.vitisvisionservice.controller.user;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.MessageSourceUtils;
import com.vitisvision.vitisvisionservice.user.service.UserService;
import com.vitisvision.vitisvisionservice.user.dto.ChangePasswordRequest;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
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

/**
 * Controller class for user management
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Endpoints for user management")
public class UserController {

    /**
     * Service class for user management
     */
    private final UserService userService;

    /**
     * MessageSourceUtils object for using the message source.
     */
    private final MessageSourceUtils messageSourceUtils;

    /**
     * Change the password of the authenticated user
     *
     * @param request   ChangePasswordRequest object
     * @param principal Principal object
     * @return ResponseEntity object
     */
    @Operation(
            summary = "Change the password of the authenticated user",
            description = "Change the password of the authenticated user with the provided details"
    )
    @PatchMapping
    public ResponseEntity<ApiResponse<String>> changePassword(
            @RequestBody @Valid ChangePasswordRequest request, Principal principal
    ) {
        userService.changePassword(request, principal);
        return ResponseEntity.ok(ApiResponse.success(messageSourceUtils.getLocalizedMessage("password.change.success"), HttpStatus.OK.value()));
    }

}
