package com.vitisvision.vitisvisionservice.user.dto;

import com.vitisvision.vitisvisionservice.common.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * UserResponse class is a DTO class for holding the user response details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UserResponse extends BaseResponse {

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The email of the user. Application insures that this is unique.
     */
    private String email;

    /**
     * The role of the user.
     */
    private String role;

    /**
     * The vineyard id of the user.
     */
    private String vineyardId;

    /**
     * The vineyard name of the user.
     */
    private boolean isBlocked;


}
