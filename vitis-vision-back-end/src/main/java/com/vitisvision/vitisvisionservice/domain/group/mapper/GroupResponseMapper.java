package com.vitisvision.vitisvisionservice.domain.group.mapper;

import com.vitisvision.vitisvisionservice.domain.group.dto.GroupResponse;
import com.vitisvision.vitisvisionservice.domain.group.entity.Group;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * GroupResponseMapper class.
 * This class is used to map the group entity to group response.
 */
@Service
public class GroupResponseMapper implements Function<Group, GroupResponse>{

    @Override
    public GroupResponse apply(Group group) {
        LocalDateTime lastUpdatedAt = group.getLastUpdatedAt();
        return GroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .formationReason(group.getFormationReason())
                .createdAt(group.getCreatedAt().toString())
                .lastUpdatedAt(lastUpdatedAt != null ? lastUpdatedAt.toString() : null)
                .createdBy(group.getCreatedBy())
                .lastUpdatedBy(group.getLastUpdatedBy())
                .build();
    }
}