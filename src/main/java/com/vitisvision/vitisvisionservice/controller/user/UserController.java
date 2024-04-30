package com.vitisvision.vitisvisionservice.controller.user;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.MessageSourceUtils;
import com.vitisvision.vitisvisionservice.user.dto.UserRequest;
import com.vitisvision.vitisvisionservice.user.dto.UserResponse;
import com.vitisvision.vitisvisionservice.user.service.UserService;
import com.vitisvision.vitisvisionservice.user.dto.ChangePasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Get the currently authenticated user
     *
     * @param principal Principal object
     * @return ResponseEntity object
     */
    @Operation(
            summary = "Get the currently authenticated user",
            description = "Get the details of the currently authenticated user"
    )
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(userService.getAuthenticatedUser(principal), HttpStatus.OK.value()));
    }

    /**
     * Update the details of the user with the provided details
     *
     * @param userResponse UserResponse object
     * @return ResponseEntity object
     */
    @Operation(
            summary = "Update user",
            description = "Update the details of the user with the provided details"
    )
    @PutMapping
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @RequestBody @Valid UserRequest userResponse,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateUser(userResponse, principal), HttpStatus.OK.value()));
    }

    /**
     * Delete the currently authenticated user
     *
     * @param principal Principal object
     * @return ResponseEntity object
     */
    @Operation(
            summary = "Delete user",
            description = "Delete the currently authenticated user"
    )
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUser(Principal principal) {
        userService.deleteUser(principal);
        return ResponseEntity.ok(ApiResponse.success(null , HttpStatus.OK.value()));
    }

    /**
     * Get the details of the user with the given id
     *
     * @param id the id of the user
     * @return ResponseEntity object
     */
    @Operation(
            summary = "Get user with id",
            description = "Get the details of the user with the given id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getUser(id), HttpStatus.OK.value()));
    }

    // TODO get all users
    // TODO delete user by id
    // TODO block user
    // TODO unblock user
}
