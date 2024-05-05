package com.vitisvision.vitisvisionservice.domain.staff.service;

import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;
import com.vitisvision.vitisvisionservice.domain.staff.dto.StaffRequest;
import com.vitisvision.vitisvisionservice.domain.staff.dto.StaffResponse;
import com.vitisvision.vitisvisionservice.domain.staff.enumeration.StaffRole;
import com.vitisvision.vitisvisionservice.domain.staff.mapper.StaffResponseMapper;
import com.vitisvision.vitisvisionservice.domain.vinayard.service.VineyardService;
import com.vitisvision.vitisvisionservice.user.entity.User;
import com.vitisvision.vitisvisionservice.user.enumeration.Role;
import com.vitisvision.vitisvisionservice.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;

/**
 * Service class for managing staff details in a vineyard
 */
@Service
@RequiredArgsConstructor
public class StaffService {

    /**
     * The service class dependency for handling vineyard details management
     */
    private final VineyardService vineyardService;

    /**
     * The service class dependency for handling user details management
     */
    private final UserService userService;

    /**
     * The mapper class dependency for mapping staff response
     */
    private final StaffResponseMapper staffResponseMapper;

    /**
     * Hire a staff in a vineyard
     *
     * @param vineyardId   the vineyard id
     * @param staffRequest the staff details
     * @param principal    the authenticated user
     */
    @PreAuthorize("hasAuthority('staff:hire')")
    @Transactional
    public void hireStaff(Integer vineyardId, StaffRequest staffRequest, Principal principal) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);

        // Check if the requested role is valid and get the vineyard role
        Role role = getRequestedRole(staffRequest);

        // Ensure the user is not already participating in the vineyard and assign the requested role
        User user = vineyardService.ensureUserNotParticipatingInVineyard(staffRequest.getStaffId());

        user.setRole(role);
        user.setVineyard(vineyardService.getVineyardById(vineyardId));

        // assume that the user is saved to the database by transaction commit
    }

    /**
     * Fire a staff in a vineyard
     *
     * @param vineyardId   the vineyard id
     * @param staffRequest the staff details
     * @param principal    the authenticated user
     */
    @PreAuthorize("hasAuthority('staff:fire')")
    @Transactional
    public void fireStaff(Integer vineyardId, StaffRequest staffRequest, Principal principal) {
        // Ensure principal is a participant in the vineyard
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        // Ensure the staff to be fired is a participant in the vineyard
        User firedUser = vineyardService.ensureVineyardParticipation(vineyardId, staffRequest.getStaffId());

        if (Objects.equals(firedUser.getEmail(), principal.getName())) {
            throw new ResourceNotFoundException("cannot.fire.self.error");
        }

        // Remove the vineyard from the user and assign the role to the USER, effectively firing the user
        firedUser.setVineyard(null);
        firedUser.setRole(Role.USER);

        // assume that the user is saved to the database by transaction commit
    }

    /**
     * Get all staff in a vineyard
     *
     * @param vineyardId the vineyard id
     * @param principal  the authenticated user
     * @param pageable   the pageable details
     * @return the page containing the staff details
     */
    @PreAuthorize("hasAuthority('staff:read')")
    @Transactional
    public Page<StaffResponse> getAllStaff(Integer vineyardId, Principal principal, Pageable pageable) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        return userService.getAllUsersByVineyardId(vineyardId, pageable).map(staffResponseMapper);
    }

    /**
     * Update staff details in a vineyard
     *
     * @param vineyardId   the vineyard id
     * @param staffRequest the staff details
     * @param principal    the authenticated user
     * @return the updated staff details
     */
    @PreAuthorize("hasAuthority('staff:write')")
    @Transactional
    public StaffResponse updateStaff(Integer vineyardId, StaffRequest staffRequest, Principal principal) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        User user = vineyardService.ensureVineyardParticipation(vineyardId, staffRequest.getStaffId());
        user.setRole(getRequestedRole(staffRequest));

        // assume that the user is saved to the database by transaction commit

        return staffResponseMapper.apply(user);
    }

    /**
     * Get the requested role from the staff request
     *
     * @param staffRequest the staff request
     * @return the requested role
     */
    private Role getRequestedRole(StaffRequest staffRequest) {
        StaffRole requestedRole;
        try {
            requestedRole = StaffRole.valueOf(staffRequest.getVineyardRole());
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("invalid.staff.role");
        }
        return Role.valueOf(requestedRole.name());
    }
}
