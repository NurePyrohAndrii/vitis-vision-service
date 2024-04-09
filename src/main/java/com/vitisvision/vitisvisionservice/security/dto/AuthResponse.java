package com.vitisvision.vitisvisionservice.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthResponse class is used to send the response of the authentication request.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    /**
     * The access token sent to the authenticated user.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * The refresh token sent to the authenticated user.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
}
