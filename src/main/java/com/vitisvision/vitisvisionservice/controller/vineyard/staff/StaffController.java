package com.vitisvision.vitisvisionservice.controller.vineyard.staff;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.response.PageableResponse;
import com.vitisvision.vitisvisionservice.common.util.PaginationUtils;
import com.vitisvision.vitisvisionservice.domain.staff.dto.StaffRequest;
import com.vitisvision.vitisvisionservice.domain.staff.dto.StaffResponse;
import com.vitisvision.vitisvisionservice.domain.staff.service.StaffService;
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
 * Controller class for managing staff details in a vineyard
 */
@RestController
@RequestMapping("/api/v1/vineyards/{vineyardId}/staff")
@RequiredArgsConstructor
@Tag(name = "Staff", description = "Endpoints for managing staff details in a vineyard")
public class StaffController {

    /**
     * The service class dependency for handling staff details management
     */
    private final StaffService staffService;

    /**
     * The utility class dependency for handling pagination details
     */
    private final PaginationUtils paginationUtils;

    /**
     * Hire a staff in a vineyard
     *
     * @param vineyardId   the vineyard id
     * @param staffRequest the staff details
     * @param principal    the authenticated user
     * @return the response entity containing the response code
     */
    @Operation(
            summary = "Hire a staff",
            description = "Hire a staff in a vineyard by providing the staff details and vineyard id"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> hireStaff(
            @PathVariable Integer vineyardId,
            @RequestBody @Valid StaffRequest staffRequest,
            Principal principal
    ) {
        staffService.hireStaff(vineyardId, staffRequest, principal);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }

    /**
     * Get all staff in a vineyard
     *
     * @param vineyardId the vineyard id
     * @param principal  the authenticated user
     * @param pageable   the pageable details
     * @return the response entity containing the response object with the staff details
     */
    @Operation(
            summary = "Get all staff",
            description = "Get all staff in a vineyard by providing the vineyard id, authenticated user and pageable details"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PageableResponse<List<StaffResponse>>>> getAllStaff(
            @PathVariable Integer vineyardId,
            Principal principal,
            @PageableDefault(sort = "role") Pageable pageable
    ) {
        Page<StaffResponse> staff = staffService.getAllStaff(vineyardId, principal, pageable);
        return ResponseEntity.ok()
                .headers(paginationUtils.createPaginationHeaders(staff, pageable))
                .body(ApiResponse.success(PageableResponse.of(staff), HttpStatus.OK.value()));
    }

    /**
     * Update staff details in a vineyard
     *
     * @param vineyardId   the vineyard id
     * @param staffRequest the staff details
     * @param principal    the authenticated user
     * @return the response entity containing the response object with the staff details
     */
    @Operation(
            summary = "Update staff",
            description = "Update staff details in a vineyard by providing the vineyard id and staff details"
    )
    @PutMapping
    public ResponseEntity<ApiResponse<StaffResponse>> updateStaff(
            @PathVariable Integer vineyardId,
            @RequestBody @Valid StaffRequest staffRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(staffService.updateStaff(vineyardId, staffRequest, principal), HttpStatus.OK.value()));
    }

    /**
     * Fire a staff in a vineyard
     *
     * @param vineyardId   the vineyard id
     * @param staffRequest the staff details
     * @param principal    the authenticated user
     * @return the response entity containing the response code
     */
    @Operation(
            summary = "Fire a staff",
            description = "Fire a staff in a vineyard by providing the staff details and vineyard id"
    )
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> fireStaff(
            @PathVariable Integer vineyardId,
            @RequestBody @Valid StaffRequest staffRequest,
            Principal principal
    ) {
        staffService.fireStaff(vineyardId, staffRequest, principal);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }

}
