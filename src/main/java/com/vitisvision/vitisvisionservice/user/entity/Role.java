package com.vitisvision.vitisvisionservice.user.entity;

/**
 * Role enum for user roles used in the application.
 */
public enum Role {

    /**
     * User role. Default role for all users. Determines the access level of the user.
     */
    USER,

    /**
     * Admin role. Higher access level than user. Can perform admin operations on users.
     */
    ADMIN,

    /**
     * The role of the owner. Can perform operations on their own vineyard account and manage users who are part of their staff.
     */
    OWNER,

    /**
     * The role of the manager. Can perform operations on the vineyard account they are assigned to and manage users who are part of their staff.
     */
    MANAGER
}
