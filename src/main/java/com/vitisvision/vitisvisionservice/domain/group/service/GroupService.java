package com.vitisvision.vitisvisionservice.domain.group.service;

import com.vitisvision.vitisvisionservice.domain.group.dto.GroupRequest;
import com.vitisvision.vitisvisionservice.domain.group.dto.GroupResponse;
import com.vitisvision.vitisvisionservice.domain.group.entity.Group;
import com.vitisvision.vitisvisionservice.domain.group.exception.GroupDuplicationException;
import com.vitisvision.vitisvisionservice.domain.group.exception.GroupNotFoundException;
import com.vitisvision.vitisvisionservice.domain.group.mapper.GroupRequestMapper;
import com.vitisvision.vitisvisionservice.domain.group.mapper.GroupResponseMapper;
import com.vitisvision.vitisvisionservice.domain.group.repository.GroupRepository;
import com.vitisvision.vitisvisionservice.domain.vinayard.mapper.VineyardResponseMapper;
import com.vitisvision.vitisvisionservice.domain.vinayard.service.VineyardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

/**
 * Group service class. This class is used to handle all group related operations.
 */
@Service
@RequiredArgsConstructor
public class GroupService {

    /**
     * Repository class for handling group operations in the database
     */
    private final GroupRepository groupRepository;

    /**
     * Service class for handling vineyard operations
     */
    private final VineyardService vineyardService;

    /**
     * Mapper class for mapping group request to group entity
     */
    private final GroupRequestMapper groupRequestMapper;

    /**
     * Mapper class for mapping group entity to group response
     */
    private final GroupResponseMapper groupResponseMapper;

    /**
     * Create a new group in a vineyard
     *
     * @param groupRequest the request object containing the group details
     * @param vineyardId   the id of the vineyard to which the group belongs
     * @param principal    the principal object containing the user details
     * @return the response object containing the group details
     */
    @PreAuthorize("hasAuthority('group:write')")
    @Transactional
    public GroupResponse createGroup(GroupRequest groupRequest, Integer vineyardId, Principal principal) {
        // ensure that the user is a part of the vineyard
        vineyardService.ensureVineyardParticipation(vineyardId, principal);

        // ensure that the group does not already exist in the vineyard
        if (groupRepository.existsByNameAndVineyardId(groupRequest.getName(), vineyardId)) {
            throw new GroupDuplicationException("group.duplicate.error");
        }

        // create the group entity
        Group cretedGroup = groupRequestMapper.apply(groupRequest);
        cretedGroup.setVineyard(vineyardService.getVineyardById(vineyardId));

        // save the group entity and return the response
        return groupResponseMapper.apply(groupRepository.save(cretedGroup));
    }

    /**
     * Get all groups in a vineyard
     *
     * @param pageable   the pageable object containing the pagination details
     * @param vineyardId the id of the vineyard to which the groups belong
     * @param principal  the principal object containing the user details
     * @return the page object containing the list of groups
     */
    @PreAuthorize("hasAuthority('group:read')")
    @Transactional
    public Page<GroupResponse> getGroups(Pageable pageable, Integer vineyardId, Principal principal) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        return groupRepository.findAllByVineyardId(vineyardId, pageable)
                .map(groupResponseMapper);
    }

    /**
     * Get a group in a vineyard
     *
     * @param vineyardId the id of the vineyard to which the group belongs
     * @param groupId    the id of the group to get
     * @param principal  the principal object containing the user details
     * @return the response object containing the group details
     */
    @PreAuthorize("hasAuthority('group:read')")
    @Transactional
    public GroupResponse getGroup(Integer vineyardId, Integer groupId, Principal principal) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        return groupResponseMapper.apply(
                groupRepository.findById(groupId)
                        .orElseThrow(() -> new GroupNotFoundException("group.not.found.error"))
        );
    }

    /**
     * Update a group in a vineyard
     *
     * @param groupRequest the request object containing the group details
     * @param vineyardId   the id of the vineyard to which the group belongs
     * @param groupId      the id of the group to update
     * @param principal    the principal object containing the user details
     * @return the response object containing the updated group details
     */
    @PreAuthorize("hasAuthority('group:write')")
    @Transactional
    public GroupResponse updateGroup(
            GroupRequest groupRequest,
            Integer vineyardId, Integer groupId,
            Principal principal
    ) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        ensureGroupExistence(groupId, vineyardId);

        // ensure that the group does not already exist in the vineyard with the provided name
        Optional<Group> groupByRequestName = groupRepository.findByNameAndVineyardId(groupRequest.getName(), vineyardId);
        if (groupByRequestName.isPresent() && !groupByRequestName.get().getId().equals(groupId)) {
            throw new GroupDuplicationException("group.duplicate.error");
        }

        // update the group entity object
        Group updatedGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("group.not.found.error"));
        groupRequestMapper.update(groupRequest, updatedGroup);

        // save the updated group entity and return the response
        return groupResponseMapper.apply(groupRepository.save(updatedGroup));
    }

    /**
     * Delete a group in a vineyard
     *
     * @param vineyardId the id of the vineyard to which the group belongs
     * @param groupId    the id of the group to delete
     * @param principal  the principal object containing the user details
     */
    @PreAuthorize("hasAuthority('group:delete')")
    @Transactional
    public void deleteGroup(Integer vineyardId, Integer groupId, Principal principal) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        ensureGroupExistence(groupId, vineyardId);
        groupRepository.deleteById(groupId);
    }

    /**
     * Ensure that the group exists in the vineyard
     *
     * @param groupId    the id of the group to check
     * @param vineyardId the id of the vineyard to which the group belongs
     */
    private void ensureGroupExistence(Integer groupId, Integer vineyardId) {
        if (!groupRepository.existsByIdAndVineyardId(groupId, vineyardId)) {
            throw new GroupNotFoundException("group.not.found.error");
        }
    }
}
