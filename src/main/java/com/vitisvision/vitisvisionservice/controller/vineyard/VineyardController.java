package com.vitisvision.vitisvisionservice.controller.vineyard;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import com.vitisvision.vitisvisionservice.common.util.MessageSourceUtils;
import com.vitisvision.vitisvisionservice.domain.vinayard.dto.CreateVineyardRequest;
import com.vitisvision.vitisvisionservice.domain.vinayard.service.VineyardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param createVineyardRequest the request object containing the vineyard details
     * @param principal       the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Create a new vineyard",
            description = "Create a new vineyard with the provided details"
    )
    @PostMapping
    public ResponseEntity<?> createVineyard(
            @RequestBody @Valid CreateVineyardRequest createVineyardRequest, Principal principal
    ) {
        return ResponseEntity.created(URI.create("ap1/v1/vineyards/" + vineyardService.createVineyard(createVineyardRequest, principal)))
                .body(ApiResponse.success(messageSourceUtils.getLocalizedMessage("vineyard.create.success"), HttpStatus.CREATED.value()));
    }

}
