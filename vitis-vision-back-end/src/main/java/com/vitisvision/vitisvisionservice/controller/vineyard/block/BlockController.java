package com.vitisvision.vitisvisionservice.controller.vineyard.block;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.response.PageableResponse;
import com.vitisvision.vitisvisionservice.common.util.PaginationUtils;
import com.vitisvision.vitisvisionservice.domain.block.dto.BlockReportRequest;
import com.vitisvision.vitisvisionservice.domain.block.dto.BlockRequest;
import com.vitisvision.vitisvisionservice.domain.block.dto.BlockResponse;
import com.vitisvision.vitisvisionservice.domain.block.service.BlockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * Controller class for managing block details in a vineyard
 */
@RestController
@RequestMapping("/api/v1/vineyards/{vineyardId}/blocks")
@RequiredArgsConstructor
@Tag(name = "Block", description = "Endpoints for managing block details in a vineyard")
public class BlockController {

    /**
     * The service class dependency for handling block details management
     */
    private final BlockService blockService;

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
            summary = "Create a new block in a vineyard",
            description = "Create a new block in a vineyard with the provided details"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<BlockResponse>> creteBlock(
            @PathVariable Integer vineyardId,
            @RequestBody @Valid BlockRequest vineyardRequest,
            Principal principal
    ) {
        BlockResponse blockResponse = blockService.createBlock(vineyardRequest, vineyardId, principal);
        return ResponseEntity.created(URI.create("ap1/v1/vineyards/" + vineyardId + "/blocks/" + blockResponse.getId()))
                .body(ApiResponse.success(blockResponse, HttpStatus.CREATED.value()));
    }

    /**
     * Get all blocks in a vineyard
     *
     * @param pageable   the pageable object containing the pagination details
     * @param vineyardId the id of the vineyard to which the blocks belong
     * @param principal  the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Get all blocks in a vineyard",
            description = "Get all blocks in a vineyard with the provided details"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PageableResponse<List<BlockResponse>>>> getBlocks(
            @PageableDefault(size = 5, sort = "name") Pageable pageable,
            @PathVariable Integer vineyardId,
            Principal principal
    ) {
        Page<BlockResponse> blocks = blockService.getBlocks(pageable, vineyardId, principal);
        return ResponseEntity.ok()
                .headers(paginationUtils.createPaginationHeaders(blocks, pageable))
                .body(ApiResponse.success(PageableResponse.of(blocks), HttpStatus.OK.value()));
    }

    /**
     * Get a block by id in a vineyard
     *
     * @param vineyardId the id of the vineyard to which the block belongs
     * @param blockId    the id of the block to get
     * @param principal  the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Get a block in a vineyard",
            description = "Get a block in a vineyard with the provided details"
    )
    @GetMapping("/{blockId}")
    public ResponseEntity<ApiResponse<BlockResponse>> getBlock(
            @PathVariable Integer vineyardId,
            @PathVariable Integer blockId,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(blockService.getBlock(vineyardId, blockId, principal), HttpStatus.OK.value()));
    }

    /**
     * Update a block in a vineyard
     *
     * @param vineyardId   the id of the vineyard to which the block belongs
     * @param blockId      the id of the block to be updated
     * @param blockRequest the request object containing the block details
     * @param principal    the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Update a block in a vineyard",
            description = "Update a block in a vineyard with the provided details"
    )
    @PutMapping("/{blockId}")
    public ResponseEntity<ApiResponse<BlockResponse>> updateBlock(
            @PathVariable Integer vineyardId,
            @PathVariable Integer blockId,
            @RequestBody @Valid BlockRequest blockRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(ApiResponse.success(blockService.updateBlock(blockRequest, vineyardId, blockId, principal), HttpStatus.OK.value()));
    }

    /**
     * Delete a block in a vineyard
     *
     * @param vineyardId the id of the vineyard to which the block belongs
     * @param blockId    the id of the block to be deleted
     * @param principal  the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Delete a block in a vineyard",
            description = "Delete a block in a vineyard with the provided details"
    )
    @DeleteMapping("/{blockId}")
    public ResponseEntity<ApiResponse<Void>> deleteBlock(
            @PathVariable Integer vineyardId,
            @PathVariable Integer blockId,
            Principal principal
    ) {
        blockService.deleteBlock(vineyardId, blockId, principal);
        return ResponseEntity.ok(ApiResponse.success(null, HttpStatus.OK.value()));
    }

    /**
     * Generate a report for a block in a vineyard
     *
     * @param vineyardId         the id of the vineyard to which the block belongs
     * @param blockId            the id of the block to generate the report for
     * @param blockReportRequest the request object containing the report details
     * @param principal          the principal object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Generate a report for a block in a vineyard",
            description = "Generate a report for a block in a vineyard with the provided details"
    )
    @PostMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, value = "{blockId}/_report")
    public ResponseEntity<byte[]> generateBlockReport(
            @PathVariable Integer vineyardId,
            @PathVariable Integer blockId,
            @RequestBody @Valid BlockReportRequest blockReportRequest,
            Principal principal
    ) {
        byte[] csvContent = blockService.generateBlockReport(blockReportRequest, vineyardId, blockId, principal);

        HttpHeaders headers = new HttpHeaders();
        String reportName = "report-block-" + blockId + ".csv";
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportName);
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE));

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvContent);
    }

}
