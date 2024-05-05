package com.vitisvision.vitisvisionservice.domain.group.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * VinesGroupAssignmentRequest class is a request class for assigning vines to a group.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VinesGroupAssignmentRequest {

    /**
     * Vine ids to be assigned to a group.
     */
    @NotNull(message = "invalid.vine.ids")
    private List<Integer> vineIds;
}
