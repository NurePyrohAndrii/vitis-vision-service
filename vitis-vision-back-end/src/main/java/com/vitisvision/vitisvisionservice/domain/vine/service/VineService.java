package com.vitisvision.vitisvisionservice.domain.vine.service;

import com.vitisvision.vitisvisionservice.domain.block.service.BlockService;
import com.vitisvision.vitisvisionservice.domain.vine.dto.VineRequest;
import com.vitisvision.vitisvisionservice.domain.vine.dto.VineResponse;
import com.vitisvision.vitisvisionservice.domain.vine.entity.Vine;
import com.vitisvision.vitisvisionservice.domain.vine.exception.VineDuplicationException;
import com.vitisvision.vitisvisionservice.domain.vine.exception.VineNotFoundException;
import com.vitisvision.vitisvisionservice.domain.vine.mapper.VineRequestMapper;
import com.vitisvision.vitisvisionservice.domain.vine.mapper.VineResponseMapper;
import com.vitisvision.vitisvisionservice.domain.vine.repository.VineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * VineService class is a service class for vine operations.
 */
@Service
@RequiredArgsConstructor
public class VineService {

    /**
     * VineRepository object is used to access the vine data.
     */
    private final VineRepository vineRepository;

    /**
     * BlockService object is used to access the block operations.
     */
    private final BlockService blockService;

    /**
     * VineRequestMapper object is used to map the vine request to vine entity.
     */
    private final VineRequestMapper vineRequestMapper;

    /**
     * VineResponseMapper object is used to map the vine entity to vine response.
     */
    private final VineResponseMapper vineResponseMapper;

    /**
     * Create a new vine in a block with the provided details
     *
     * @param vineRequest the request object containing the vine details
     * @param blockId     the block id
     * @param principal   the principal object containing the user details
     * @return the response object containing the vine details
     */
    @PreAuthorize("hasAuthority('vine:write')")
    @Transactional
    public VineResponse createVine(VineRequest vineRequest, Integer blockId, Principal principal) {
        // Ensure that the user has access to the block
        blockService.ensureBlockAccess(blockId, principal);

        // Check if the vine position is available. Position means the vine number and row number in a block.
        if (vineRepository.existsByVineNumberAndRowNumberAndBlock_Id(vineRequest.getVineNumber(), vineRequest.getRowNumber(), blockId)) {
            throw new VineDuplicationException("vine.duplicate.error");
        }

        // Create a new vine entity
        Vine vine = vineRequestMapper.apply(vineRequest);
        vine.setBlock(blockService.getBlockById(blockId));

        // Save the vine entity
        return vineResponseMapper.apply(vineRepository.save(vine));
    }

    /**
     * Get the vine details in a block with the provided details
     *
     * @param blockId   the block id
     * @param vineId    the vine id
     * @param principal the principal object containing the user details
     * @return the response entity containing the response object
     */
    @PreAuthorize("hasAuthority('vine:read')")
    @Transactional
    public VineResponse getVine(Integer blockId, Integer vineId, Principal principal) {
        // Ensure that the user has access to the block
        blockService.ensureBlockAccess(blockId, principal);
        return vineRepository.findByIdAndBlock_Id(vineId, blockId)
                .map(vineResponseMapper)
                .orElseThrow(() -> new VineNotFoundException("vine.not.found.error"));
    }

    /**
     * Get all vines in a block
     *
     * @param pageable  the pageable object containing the pagination details
     * @param blockId   the block id
     * @param principal the principal object containing the user details
     * @return the response entity containing the response object
     */
    @PreAuthorize("hasAuthority('vine:read')")
    @Transactional
    public Page<VineResponse> getVines(Pageable pageable, Integer blockId, Principal principal) {
        // Ensure that the user has access to the block
        blockService.ensureBlockAccess(blockId, principal);
        return vineRepository.findAllByBlock_Id(blockId, pageable)
                .map(vineResponseMapper);
    }

    /**
     * Update the vine details in a block with the provided details
     *
     * @param vineRequest the request object containing the vine details
     * @param blockId     the block id
     * @param vineId      the vine id
     * @param principal   the principal object containing the user details
     * @return the response entity containing the response object
     */
    @PreAuthorize("hasAuthority('vine:write')")
    @Transactional
    public VineResponse updateVine(VineRequest vineRequest, Integer blockId, Integer vineId, Principal principal) {
        // Ensure that the user has access to the block and the vine exists in the block
        blockService.ensureBlockAccess(blockId, principal);
        ensureVineInBlockExistence(vineId, blockId);

        // Check if the requested vine position is available for the update
        Optional<Vine> updatedVine = vineRepository.findByVineNumberAndRowNumberAndBlock_Id(
                vineRequest.getVineNumber(), vineRequest.getRowNumber(), blockId);

        if (updatedVine.isPresent() && !updatedVine.get().getId().equals(vineId)) {
            throw new VineDuplicationException("vine.duplicate.error");
        }

        // Get the vine entity to be updated
        Vine vine = vineRepository.findById(vineId)
                .orElseThrow(() -> new VineNotFoundException("vine.not.found.error"));

        // Update the vine entity
        vineRequestMapper.update(vineRequest, vine);

        // Save the updated vine entity and return the response
        return vineResponseMapper.apply(vineRepository.save(vine));
    }

    /**
     * Delete the vine in a block with the provided details
     *
     * @param blockId   the block id
     * @param vineId    the vine id
     * @param principal the principal object containing the user details
     */
    @PreAuthorize("hasAuthority('vine:delete')")
    @Transactional
    public void deleteVine(Integer blockId, Integer vineId, Principal principal) {
        // Ensure that the user has access to the block
        blockService.ensureBlockAccess(blockId, principal);
        ensureVineInBlockExistence(vineId, blockId);

        Vine vine = vineRepository.findById(vineId)
                .orElseThrow(() -> new VineNotFoundException("vine.not.found.error"));

        // Remove the vine from the groups
        vine.getGroups().forEach(
                group -> group.getVines().remove(vine)
        );

        // Delete the vine entity
        vineRepository.deleteById(vineId);
    }

    /**
     * Get all vines with the provided ids
     *
     * @param vineIds the list of vine ids
     * @return the list of vine objects
     */
    public List<Vine> getAllVinesWithIds(List<Integer> vineIds) {
        return vineRepository.findAllById(vineIds);
    }

    /**
     * Get all vines with the provided ids and pagination details
     *
     * @param vineIds  the list of vine ids
     * @param pageable the pageable object containing the pagination details
     * @return the page object containing the vine objects
     */
    public Page<Vine> getAllVinesWithIds(List<Integer> vineIds, Pageable pageable) {
        return vineRepository.findAllByIds(pageable, vineIds);
    }

    /**
     * Get all vines not in the provided ids
     *
     * @param groupVineIds the list of vine ids
     * @param pageable     the pageable object containing the pagination details
     * @return the page object containing the vine objects
     */
    public Page<Vine> getAllVinesNotInIds(List<Integer> groupVineIds, Pageable pageable) {
        return vineRepository.findAllNotInIds(pageable, groupVineIds);
    }

    /**
     * Ensure that the vine exists in the block and the user has access to the block
     *
     * @param vineId    the vine id
     * @param principal the principal object containing the user details
     * @return the vine object
     */
    public Vine ensureVineAccess(Integer vineId, Principal principal) {
        Vine vine = vineRepository.findById(vineId)
                .orElseThrow(() -> new VineNotFoundException("vine.not.found.error"));
        blockService.ensureBlockAccess(vine.getBlock().getId(), principal);
        return vine;
    }

    /**
     * Ensure that the vine exists in the block
     *
     * @param vineId  the vine id
     * @param blockId the block id
     */
    private void ensureVineInBlockExistence(Integer vineId, Integer blockId) {
        if (!vineRepository.existsByIdAndBlock_Id(vineId, blockId)) {
            throw new VineNotFoundException("vine.not.found.error");
        }
    }
}
