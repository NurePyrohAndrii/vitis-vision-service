package com.vitisvision.vitisvisionservice.user;

/**
 * Role enum for user roles used in the application.
 */
public enum Role {

    /**
     * User role. Default role for all users. Determines the access level of the user.
     */
    USER,

    /**
     * Admin role. Higher access level than user. Can perform admin operations.
     */
    ADMIN
}
