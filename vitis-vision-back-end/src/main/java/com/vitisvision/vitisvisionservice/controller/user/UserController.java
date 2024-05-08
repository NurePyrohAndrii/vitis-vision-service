package com.vitisvision.vitisvisionservice.controller.user;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.response.PageableResponse;
import com.vitisvision.vitisvisionservice.common.util.MessageSourceUtils;
import com.vitisvision.vitisvisionservice.common.util.PaginationUtils;
import com.vitisvision.vitisvisionservice.user.dto.UserBlockRequest;
import com.vitisvision.vitisvisionservice.user.dto.UserRequest;
import com.vitisvision.vitisvisionservice.user.dto.UserResponse;
import com.vitisvision.vitisvisionservice.user.service.UserService;
import com.vitisvision.vitisvisionservice.user.dto.ChangePasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
     * Utility class for handling pagination operations such as creating pagination headers
     */
    private final PaginationUtils paginationUtils;

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

    /**
     * Get all users registered in app
     *
     * @param pageable pagination info to be applied
     * @return configured response object with user details
     */
    @Operation(
            summary = "Get all users",
            description = "Get all users registered in the application"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PageableResponse<List<UserResponse>>>> getUsers(@PageableDefault(sort = "lastName") Pageable pageable) {
        Page<UserResponse> users = userService.getUsers(pageable);
        return ResponseEntity.ok()
                .headers(paginationUtils.createPaginationHeaders(users, pageable))
                .body(ApiResponse.success(PageableResponse.of(users), HttpStatus.OK.value()));
    }

    /**
     * Delete user with given id
     *
     * @param userId user id to be deleted
     * @return response entity with response code
     */
    @Operation(
            summary = "Delete user with id",
            description = "Delete the user with the given id"
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserById(@PathVariable Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }

    /**
     * Block user with given details
     *
     * @param request details of user to be blocked
     * @return response entity with status code
     */
    @Operation(
            summary = "Block user",
            description = "Block the user with the provided details"
    )
    @PostMapping("/block")
    public ResponseEntity<ApiResponse<Void>> blockUser(@RequestBody @Valid UserBlockRequest request) {
        userService.blockUser(request);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }

    /**
     * Unblock user with given details
     *
     * @param request details of user to be unblocked
     * @return response entity with status code
     */
    @Operation(
            summary = "Unblock user",
            description = "Unblock the user with the provided details"
    )
    @PostMapping("/unblock")
    public ResponseEntity<ApiResponse<Void>> unblockUser(@RequestBody @Valid UserBlockRequest request) {
        userService.unblockUser(request);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }

}
