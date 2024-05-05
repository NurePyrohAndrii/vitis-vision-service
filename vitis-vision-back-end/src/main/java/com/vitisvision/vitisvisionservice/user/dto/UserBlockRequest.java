package com.vitisvision.vitisvisionservice.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class to accept block requests info
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBlockRequest {

    /**
     * The id of user to be blocked
     */
    @NotNull(message = "invalid.user.id")
    @Min(value = 1, message = "invalid.user.id")
    @JsonProperty("userId")
    private Integer userId;
}
