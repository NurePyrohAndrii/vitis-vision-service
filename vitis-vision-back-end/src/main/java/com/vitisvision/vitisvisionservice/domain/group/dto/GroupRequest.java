package com.vitisvision.vitisvisionservice.domain.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Group request class. Represents the request object for creating and updating a group.
 */
@Data
@Builder
@AllArgsConstructor
public class GroupRequest {

    /**
     * Represents the name of the group.
     */
    @NotBlank(message = "not.blank.group.name")
    private String name;

    /**
     * Represents the description of the group.
     */
    @NotBlank(message = "not.blank.group.description")
    @Size(max = 255, message = "invalid.group.description.size")
    private String description;

    /**
     * Represents the reason for the formation of the group.
     */
    @NotBlank(message = "not.blank.group.formation.reason")
    @Size(max = 255, message = "invalid.group.formation.reason.size")
    private String formationReason;

}
