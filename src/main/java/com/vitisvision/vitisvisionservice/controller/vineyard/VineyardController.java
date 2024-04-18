package com.vitisvision.vitisvisionservice.controller.vineyard;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardRequest;
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

@RestController
@RequestMapping("/api/v1/vineyards")
@RequiredArgsConstructor
@Tag(name = "Vineyard", description = "Endpoints for managing vineyards details")
public class VineyardController {

    private final VineyardService vineyardService;

    @Operation(
            summary = "Create a new vineyard",
            description = "Create a new vineyard with the provided details"
    )
    @PostMapping
    public ResponseEntity<?> createVineyard(
            @RequestBody @Valid VineyardRequest vineyardRequest, Principal principal
    ) {
        return ResponseEntity.created(URI.create("ap1/v1/vineyards/" + vineyardService.createVineyard(vineyardRequest, principal)))
                .body(ApiResponse.success("Vineyard created successfully", HttpStatus.CREATED.value()));
    }

}
