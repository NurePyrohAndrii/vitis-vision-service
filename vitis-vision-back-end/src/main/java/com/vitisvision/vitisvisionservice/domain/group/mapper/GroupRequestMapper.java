package com.vitisvision.vitisvisionservice.domain.group.mapper;

import com.vitisvision.vitisvisionservice.domain.group.dto.GroupRequest;
import com.vitisvision.vitisvisionservice.domain.group.entity.Group;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * GroupRequestMapper class.
 * This class is used to map the group request to group entity.
 */
@Service
public class GroupRequestMapper implements Function<GroupRequest, Group> {

    @Override
    public Group apply(GroupRequest groupRequest) {
        return Group.builder()
                .name(groupRequest.getName())
                .description(groupRequest.getDescription())
                .formationReason(groupRequest.getFormationReason())
                .build();
    }

    public void update(GroupRequest groupRequest, Group updatedGroup) {
        updatedGroup.setName(groupRequest.getName());
        updatedGroup.setDescription(groupRequest.getDescription());
        updatedGroup.setFormationReason(groupRequest.getFormationReason());
    }
}
