package com.vitisvision.vitisvisionservice.user.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

import static com.vitisvision.vitisvisionservice.user.enumeration.Permission.*;

/**
 * Role enum for user roles used in the application.
 */
@Getter
@RequiredArgsConstructor
public enum Role {

    /**
     * Admin role. Higher access level than user. Can perform admin operations on users.
     */
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_WRITE,
                    ADMIN_DELETE,
                    ADMIN_BLOCK,
                    DB_BACKUP
            )
    ),

    /**
     * User role. Default role for all users. Determines the access level of the user.
     */
    USER(
            Set.of(
                    VINEYARD_READ
            )
    ),

    /**
     * The role of the worker. Can perform operations on the vineyard vines and devices.
     */
    VINEYARD_WORKER(
            Set.of(
                    GROUP_READ,
                    BLOCK_READ,
                    VINE_READ,
                    DEVICE_READ,
                    VINE_WRITE,
                    VINE_DELETE,
                    DEVICE_WRITE,
                    DEVICE_DELETE
            )
    ),

    /**
     * The role of the manager. Can perform operations on the vineyard vines, groups, blocks and devices.
     */
    VINEYARD_MANAGER(
            Set.of(
                    GROUP_WRITE,
                    GROUP_DELETE,
                    BLOCK_WRITE,
                    BLOCK_DELETE,
                    STAFF_READ,
                    DEVICE_ACTIVATE,
                    DEVICE_DEACTIVATE,
                    BLOCK_REPORT
            )
    ),

    /**
     * The role of the director. Can perform operations on all vineyard accounts and manage users. Highest access level.
     */
    VINEYARD_DIRECTOR(
            Set.of(
                    VINEYARD_WRITE,
                    VINEYARD_DELETE,
                    STAFF_HIRE,
                    STAFF_FIRE,
                    STAFF_WRITE
            )
    );

    /**
     * The set of child roles for each role.
     */
    private final Set<Role> children = new HashSet<>();

    /**
     * The set of permissions for each role.
     */
    private final Set<Permission> permissions;

    static {
        VINEYARD_DIRECTOR.children.add(VINEYARD_MANAGER);
        VINEYARD_MANAGER.children.add(VINEYARD_WORKER);
        VINEYARD_WORKER.children.add(USER);
        ADMIN.children.add(USER);
    }

    /**
     * Returns the authorities for the role as a list of SimpleGrantedAuthority objects.
     * The authorities are the permissions of the role and the permissions of the child roles.
     *
     * @return the authorities for the role
     */
    public List<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        collectChildAuthorities(this, authorities);
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return new ArrayList<>(authorities);
    }

    /**
     * Collects child's role authorities of the given role.
     *
     * @param role the role
     * @param authorities the set of authorities
     */
    private void collectChildAuthorities(Role role, Set<SimpleGrantedAuthority> authorities) {
        for (Role child : role.children) {
            for (Permission permission : child.permissions) {
                authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
            }
            collectChildAuthorities(child, authorities);
        }
    }
}
