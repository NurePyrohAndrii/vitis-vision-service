package com.vitisvision.vitisvisionservice.domain.block.service;

import com.vitisvision.vitisvisionservice.domain.block.dto.BlockRequest;
import com.vitisvision.vitisvisionservice.domain.block.dto.BlockResponse;
import com.vitisvision.vitisvisionservice.domain.block.entity.Block;
import com.vitisvision.vitisvisionservice.domain.block.exception.BlockDuplicationException;
import com.vitisvision.vitisvisionservice.domain.block.exception.BlockNotFoundException;
import com.vitisvision.vitisvisionservice.domain.block.mapper.BlockRequestMapper;
import com.vitisvision.vitisvisionservice.domain.block.mapper.BlockResponseMapper;
import com.vitisvision.vitisvisionservice.domain.block.repository.BlockRepository;
import com.vitisvision.vitisvisionservice.domain.vinayard.service.VineyardService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

/**
 * BlockService class is a service class for block operations.
 */
@Service
@RequiredArgsConstructor
public class BlockService {

    /**
     * Block repository object to perform database operations
     */
    private final BlockRepository blockRepository;

    /**
     * Vineyard service object to perform vineyard operations
     */
    private final VineyardService vineyardService;

    /**
     * Block response mapper object to map the block entity to block response
     */
    private final BlockResponseMapper blockResponseMapper;

    /**
     * Block request mapper object to map the block request to block entity
     */
    private final BlockRequestMapper blockRequestMapper;

    /**
     * Create a new block in a vineyard with the provided details
     *
     * @param blockRequest    the request object containing the block details
     * @param vineyardId      the id of the vineyard to which the block belongs
     * @param principal       the principal object containing the user details
     * @return the response object containing the block details
     */
    @PreAuthorize("hasAuthority('block:write')")
    @Transactional
    public BlockResponse createBlock(BlockRequest blockRequest, Integer vineyardId, Principal principal) {
        // Ensure the user is a participant in the vineyard
        vineyardService.ensureVineyardParticipation(vineyardId, principal);

        // Check if the block with the provided name already exists in the vineyard
        if(blockRepository.existsByNameAndVineyardId(blockRequest.getName(), vineyardId)) {
            throw new BlockDuplicationException("block.duplicate.error");
        }

        // Create the block entity object and set the vineyard
        Block createdBlock = blockRequestMapper.apply(blockRequest);
        createdBlock.setVineyard(vineyardService.getVineyardById(vineyardId));

        // Save the block entity object and return the response object
        return blockResponseMapper.apply(blockRepository.save(createdBlock));
    }

    /**
     * Get a block by id in a vineyard
     *
     * @param vineyardId the id of the vineyard to which the block belongs
     * @param blockId    the id of the block to get
     * @param principal  the principal object containing the user details
     * @return the response entity containing the response object
     */
    @PreAuthorize("hasAuthority('block:read')")
    @Transactional
    public BlockResponse getBlock(Integer vineyardId, Integer blockId, Principal principal) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        return blockResponseMapper.apply(
                blockRepository.findById(blockId)
                        .orElseThrow(() -> new BlockNotFoundException("block.not.found.error"))
        );
    }

    /**
     * Get all blocks in a vineyard with the provided id
     *
     * @param pageable the pageable object containing the pagination details
     * @param vineyardId the id of the vineyard
     * @param principal the principal object containing the user details
     * @return the page object containing the blocks in the vineyard
     */
    @PreAuthorize("hasAuthority('block:read')")
    @Transactional
    public Page<BlockResponse> getBlocks(Pageable pageable, Integer vineyardId, Principal principal) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        return blockRepository.findAllByVineyard_Id(pageable, vineyardId)
                .map(blockResponseMapper);
    }

    /**
     * Update a block in a vineyard with the provided details
     *
     * @param blockRequest the request object containing the block details
     * @param vineyardId   the id of the vineyard to which the block belongs
     * @param blockId      the id of the block to be updated
     * @param principal    the principal object containing the user details
     * @return the response entity containing the response object
     */
    @PreAuthorize("hasAuthority('block:write')")
    @Transactional
    public BlockResponse updateBlock(
            @Valid BlockRequest blockRequest,
            Integer vineyardId, Integer blockId,
            Principal principal
    ) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        ensureBlockExistence(blockId, vineyardId);

        // Check if the block with the provided name already exists in the vineyard, and it is a different block
        Optional<Block> blockByRequestName = blockRepository.findByNameAndVineyard_Id(blockRequest.getName(), vineyardId);
        if (blockByRequestName.isPresent() && !blockByRequestName.get().getId().equals(blockId)) {
            throw new BlockDuplicationException("block.duplicate.error");
        }

        // Update the block entity object and set the vineyard
        Block updatedBlock = blockRepository.findById(blockId)
                .orElseThrow(() -> new BlockNotFoundException("block.not.found.error"));
        blockRequestMapper.update(blockRequest, updatedBlock);
        updatedBlock.setVineyard(vineyardService.getVineyardById(vineyardId));

        // Save the block entity object and return the response object
        return blockResponseMapper.apply(blockRepository.save(updatedBlock));
    }

    /**
     * Delete a block by id in a vineyard
     *
     * @param vineyardId the id of the vineyard to which the block belongs
     * @param blockId    the id of the block to delete
     * @param principal  the principal object containing the user details
     */
    @PreAuthorize("hasAuthority('block:delete')")
    @Transactional
    public void deleteBlock(Integer vineyardId, Integer blockId, Principal principal) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        ensureBlockExistence(blockId, vineyardId);
        // TODO: Delete all associated entities
        blockRepository.deleteById(blockId);
    }

    /**
     * Ensure the existence of a block in a vineyard with the provided id
     *
     * @param blockId    the id of the block
     * @param vineyardId the id of the vineyard
     */
    private void ensureBlockExistence(Integer blockId, Integer vineyardId) {
        if (!blockRepository.existsByIdAndVineyard_Id(blockId, vineyardId)) {
            throw new BlockNotFoundException("block.not.found.error");
        }
    }

}
