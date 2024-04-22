package com.vitisvision.vitisvisionservice.controller.vineyard;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.MessageSourceUtils;
import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardRequest;
import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardResponse;
import com.vitisvision.vitisvisionservice.domain.vinayard.service.VineyardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

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
     * Utility class for handling common operations
     */
    private final MessageSourceUtils messageSourceUtils;

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
            @RequestBody @Valid VineyardRequest vineyardRequest, Principal principal
    ) {
        VineyardResponse vineyardResponse = vineyardService.createVineyard(vineyardRequest, principal);
        return ResponseEntity.created(URI.create("ap1/v1/vineyards/" + vineyardResponse.getId()))
                .body(ApiResponse.success(vineyardResponse, HttpStatus.CREATED.value()));
    }

    @Operation()
    @PutMapping("/{vineyardId}")
    public ResponseEntity<?> updateVineyard(
            @PathVariable Integer vineyardId,
            @RequestBody @Valid VineyardRequest vineyardRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(vineyardService.updateVineyard(vineyardId, vineyardRequest, principal), HttpStatus.OK.value()));
    }

}
