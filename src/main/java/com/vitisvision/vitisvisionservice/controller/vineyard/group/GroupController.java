package com.vitisvision.vitisvisionservice.controller.vineyard.group;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.response.PageableResponse;
import com.vitisvision.vitisvisionservice.common.util.PaginationUtils;
import com.vitisvision.vitisvisionservice.domain.group.dto.GroupRequest;
import com.vitisvision.vitisvisionservice.domain.group.dto.GroupResponse;
import com.vitisvision.vitisvisionservice.domain.group.service.GroupService;
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

import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * Group controller class.
 * This class is used to handle all group related operations.
 */
@RestController
@RequestMapping("/api/v1/vineyards/{vineyardId}/groups")
@RequiredArgsConstructor
@Tag(name = "Group", description = "Endpoints for managing group details in a vineyard")
public class GroupController {

    /**
     * Service class for handling group operations
     */
    private final GroupService groupService;

    /**
     * Utility class for handling pagination operations such as creating pagination headers
     */
    private final PaginationUtils paginationUtils;

    /**
     * Create a new group in a vineyard
     *
     * @param vineyardId   the id of the vineyard to which the group belongs
     * @param groupRequest the request object containing the group details
     * @param principal    the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Create a group in a vineyard",
            description = "Create a group in a vineyard with the provided details"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<GroupResponse>> createGroup(
            @PathVariable Integer vineyardId,
            @RequestBody @Valid GroupRequest groupRequest,
            Principal principal
    ) {
        GroupResponse groupResponse = groupService.createGroup(groupRequest, vineyardId, principal);
        return ResponseEntity.created(URI.create("ap1/v1/vineyards/" + vineyardId + "/groups/" + groupResponse.getId()))
                .body(ApiResponse.success(groupResponse, HttpStatus.CREATED.value()));
    }

    /**
     * Get all groups in a vineyard
     *
     * @param vineyardId the id of the vineyard to which the groups belong
     * @param pageable   the pageable object containing the pagination details
     * @param principal  the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Get all groups in a vineyard",
            description = "Get all groups in a vineyard with the provided details"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PageableResponse<List<GroupResponse>>>> getGroups(
            @PageableDefault(size = 5, sort = "name") Pageable pageable,
            @PathVariable Integer vineyardId,
            Principal principal
    ) {
        Page<GroupResponse> groups = groupService.getGroups(pageable, vineyardId, principal);
        return ResponseEntity.ok()
                .headers(paginationUtils.createPaginationHeaders(groups, pageable))
                .body(ApiResponse.success(PageableResponse.of(groups), HttpStatus.OK.value()));
    }

    /**
     * Get a group in a vineyard
     *
     * @param vineyardId the id of the vineyard to which the group belongs
     * @param groupId    the id of the group to be retrieved
     * @param principal  the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Get a group in a vineyard",
            description = "Get a group in a vineyard with the provided details"
    )
    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroup(
            @PathVariable Integer vineyardId,
            @PathVariable Integer groupId,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(groupService.getGroup(vineyardId, groupId, principal), HttpStatus.OK.value()));
    }

    /**
     * Update a group in a vineyard
     *
     * @param vineyardId   the id of the vineyard to which the group belongs
     * @param groupId      the id of the group to be updated
     * @param groupRequest the request object containing the group details
     * @param principal    the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Update a group in a vineyard",
            description = "Update a group in a vineyard with the provided details"
    )
    @PutMapping("/{groupId}")
    public ResponseEntity<ApiResponse<GroupResponse>> updateGroup(
            @PathVariable Integer vineyardId,
            @PathVariable Integer groupId,
            @RequestBody @Valid GroupRequest groupRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(groupService.updateGroup(groupRequest, vineyardId, groupId, principal), HttpStatus.OK.value()));
    }

    /**
     * Delete a group in a vineyard
     *
     * @param vineyardId the id of the vineyard to which the group belongs
     * @param groupId    the id of the group to be deleted
     * @param principal  the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Delete a group in a vineyard",
            description = "Delete a group in a vineyard with the provided details"
    )
    @DeleteMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Void>> deleteGroup(
            @PathVariable Integer vineyardId,
            @PathVariable Integer groupId,
            Principal principal
    ) {
        groupService.deleteGroup(vineyardId, groupId, principal);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }
}
