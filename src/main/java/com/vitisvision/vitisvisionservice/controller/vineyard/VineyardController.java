package com.vitisvision.vitisvisionservice.controller.vineyard;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.response.PageableResponse;
import com.vitisvision.vitisvisionservice.common.util.PaginationUtils;
import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardRequest;
import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardResponse;
import com.vitisvision.vitisvisionservice.domain.vinayard.service.VineyardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * Controller class for managing vineyard details
 */
@RestController
@RequestMapping("/api/v1/vineyards")
@RequiredArgsConstructor
@Tag(name = "Vineyard", description = "Endpoints for managing vineyards details")
public class VineyardController {

    /**
     * The service class dependency for handling vineyard details management
     */
    private final VineyardService vineyardService;

    /**
     * Utility class for handling pagination operations such as creating pagination headers
     */
    private final PaginationUtils paginationUtils;

    /**
     * Create a new vineyard with the provided details
     *
     * @param vineyardRequest the request object containing the vineyard details
     * @param principal       the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Create a new vineyard",
            description = "Create a new vineyard with the provided details"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<VineyardResponse>> createVineyard(
            @RequestBody @Valid VineyardRequest vineyardRequest,
            Principal principal
    ) {
        VineyardResponse vineyardResponse = vineyardService.createVineyard(vineyardRequest, principal);
        return ResponseEntity.created(URI.create("ap1/v1/vineyards/" + vineyardResponse.getId()))
                .body(ApiResponse.success(vineyardResponse, HttpStatus.CREATED.value()));
    }

    /**
     * Get a vineyard with the provided id
     *
     * @param vineyardId the id of the vineyard to get
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Get a vineyard",
            description = "Get a vineyard with the provided id"
    )
    @GetMapping("/{vineyardId}")
    public ResponseEntity<ApiResponse<VineyardResponse>> getVineyard(
            @PathVariable Integer vineyardId,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(vineyardService.getVineyard(vineyardId, principal), HttpStatus.OK.value()));
    }

    /**
     * Get a list of vineyards
     *
     * @param pageable the pageable object containing the pagination details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Get vineyards",
            description = "Get a list of vineyards"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PageableResponse<List<VineyardResponse>>>> getVineyards(
            @PageableDefault(size = 5, sort = "company.CompanyName") Pageable pageable
    ) {
        Page<VineyardResponse> vineyards = vineyardService.getVineyards(pageable);
        return ResponseEntity.ok()
                .headers(paginationUtils.createPaginationHeaders(vineyards, pageable))
                .body(ApiResponse.success(PageableResponse.of(vineyards), HttpStatus.OK.value()));
    }

    /**
     * Update a vineyard with the provided details
     *
     * @param vineyardId      the id of the vineyard to update
     * @param vineyardRequest the request object containing the vineyard details
     * @param principal       the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Update a vineyard",
            description = "Update a vineyard with the provided details"
    )
    @PutMapping("/{vineyardId}")
    public ResponseEntity<ApiResponse<VineyardResponse>> updateVineyard(
            @PathVariable Integer vineyardId,
            @RequestBody @Valid VineyardRequest vineyardRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(vineyardService.updateVineyard(vineyardId, vineyardRequest, principal), HttpStatus.OK.value()));
    }

    /**
     * Get a vineyard with the provided id
     *
     * @param vineyardId the id of the vineyard to get
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Delete a vineyard",
            description = "Delete a vineyard with the provided id"
    )
    @DeleteMapping("/{vineyardId}")
    public ResponseEntity<ApiResponse<Void>> deleteVineyard(
            @PathVariable Integer vineyardId,
            Principal principal
    ) {
        vineyardService.deleteVineyard(vineyardId, principal);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }

}
