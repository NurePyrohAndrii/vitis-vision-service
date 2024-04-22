package com.vitisvision.vitisvisionservice.user.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Permission enum for user permissions used in the application.
 */
@Getter
@RequiredArgsConstructor
public enum Permission {

    VINEYARD_READ("vineyard:read"),
    VINEYARD_WRITE("vineyard:write"),
    VINEYARD_DELETE("vineyard:delete"),

    GROUP_READ("group:read"),
    GROUP_WRITE("group:write"),
    GROUP_DELETE("group:delete"),

    BLOCK_READ("block:read"),
    BLOCK_WRITE("block:write"),
    BLOCK_DELETE("block:delete"),

    VINE_READ("vine:read"),
    VINE_WRITE("vine:write"),
    VINE_DELETE("vine:delete"),

    DEVICE_READ("device:read"),
    DEVICE_WRITE("device:write"),
    DEVICE_DELETE("device:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_BLOCK("admin:block");

    private final String permission;
}