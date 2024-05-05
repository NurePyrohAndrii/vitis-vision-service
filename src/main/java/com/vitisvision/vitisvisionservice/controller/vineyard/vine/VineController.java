package com.vitisvision.vitisvisionservice.controller.vineyard.vine;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.response.PageableResponse;
import com.vitisvision.vitisvisionservice.common.util.PaginationUtils;
import com.vitisvision.vitisvisionservice.domain.vine.dto.VineRequest;
import com.vitisvision.vitisvisionservice.domain.vine.dto.VineResponse;
import com.vitisvision.vitisvisionservice.domain.vine.service.VineService;
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
 * Controller class for managing vine details in a block
 */
@RestController
@RequestMapping("/api/v1/blocks/{blockId}/vines")
@RequiredArgsConstructor
@Tag(name = "Vine", description = "Endpoints for managing vine details in a block")
public class VineController {

    /**
     * The service class dependency for handling vine details management
     */
    private final VineService vineService;

    /**
     * Utility class for handling pagination operations such as creating pagination headers
     */
    private final PaginationUtils paginationUtils;

    /**
     * Create a new vine in a block with the provided details
     *
     * @param blockId     the block id
     * @param vineRequest the request object containing the vine details
     * @param principal   the principal object containing the user details
     * @return the response entity containing the response object with the vine details
     */
    @Operation(
            summary = "Create a new vine in a block",
            description = "Create a new vine in a block with the provided details"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<VineResponse>> createVine(
            @PathVariable Integer blockId,
            @RequestBody @Valid VineRequest vineRequest,
            Principal principal
    ) {
        VineResponse vineResponse = vineService.createVine(vineRequest, blockId, principal);
        return ResponseEntity.created(URI.create("ap1/v1/blocks/" + blockId + "/vines/" + vineResponse.getId()))
                .body(ApiResponse.success(vineResponse, HttpStatus.CREATED.value()));
    }

    /**
     * Get the vine details in a block with the provided details
     *
     * @param blockId the block id containing the vine
     * @param vineId the vine id
     * @param principal the principal object containing the user details
     * @return the response entity containing the response object with the vine details
     */
    @Operation(
            summary = "Get the vine details in a block",
            description = "Get the vine details in a block with the provided details"
    )
    @GetMapping("/{vineId}")
    public ResponseEntity<ApiResponse<VineResponse>> getVine(
            @PathVariable Integer blockId,
            @PathVariable Integer vineId,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(vineService.getVine(blockId, vineId, principal), HttpStatus.OK.value()));
    }

    /**
     * Get all vines in a block
     *
     * @param pageable  the pageable object containing the pagination details
     * @param blockId   the block id containing the vines
     * @param principal the principal object containing the user details
     * @return the response entity containing the response object with the vine details
     */
    @Operation(
            summary = "Get all vines in a block",
            description = "Get all vines in a block with the provided details"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PageableResponse<List<VineResponse>>>> getVines(
            @PageableDefault(size = 20, sort = "rowNumber") Pageable pageable,
            @PathVariable Integer blockId,
            Principal principal
    ) {
        Page<VineResponse> vines = vineService.getVines(pageable, blockId, principal);
        return ResponseEntity.ok()
                .headers(paginationUtils.createPaginationHeaders(vines, pageable))
                .body(ApiResponse.success(PageableResponse.of(vines), HttpStatus.OK.value()));
    }

    /**
     * Update the vine details in a block with the provided details
     *
     * @param vineRequest the request object containing the vine details
     * @param principal   the principal object containing the user details
     * @return the response entity containing the response object with the vine details
     */
    @Operation(
            summary = "Update the vine details in a block",
            description = "Update the vine details in a block with the provided details"
    )
    @PutMapping("/{vineId}")
    public ResponseEntity<ApiResponse<VineResponse>> updateVine(
            @PathVariable Integer blockId,
            @PathVariable Integer vineId,
            @RequestBody @Valid VineRequest vineRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(vineService.updateVine(vineRequest, blockId, vineId, principal), HttpStatus.OK.value()));
    }

    /**
     * Delete the vine details in a block with the provided details
     *
     * @param blockId the block id containing the vine
     * @param vineId the vine id
     * @param principal the principal object containing the user details
     * @return the response entity containing the response object with the vine details
     */
    @Operation(
            summary = "Delete the vine details in a block",
            description = "Delete the vine details in a block with the provided details"
    )
    @DeleteMapping("/{vineId}")
    public ResponseEntity<ApiResponse<Void>> deleteVine(
            @PathVariable Integer blockId,
            @PathVariable Integer vineId,
            Principal principal
    ) {
        vineService.deleteVine(blockId, vineId, principal);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }
}
