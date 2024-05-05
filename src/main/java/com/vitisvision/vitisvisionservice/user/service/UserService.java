package com.vitisvision.vitisvisionservice.user.service;

import com.vitisvision.vitisvisionservice.user.dto.ChangePasswordRequest;
import com.vitisvision.vitisvisionservice.user.dto.UserBlockRequest;
import com.vitisvision.vitisvisionservice.user.dto.UserRequest;
import com.vitisvision.vitisvisionservice.user.dto.UserResponse;
import com.vitisvision.vitisvisionservice.user.entity.User;
import com.vitisvision.vitisvisionservice.user.enumeration.Role;
import com.vitisvision.vitisvisionservice.user.exception.ChangePasswordException;
import com.vitisvision.vitisvisionservice.user.exception.UserNotFoundException;
import com.vitisvision.vitisvisionservice.user.mapper.UserResponseMapper;
import com.vitisvision.vitisvisionservice.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * Service class for user operations. Implements the UserDetailsService interface to be used by Spring Security.
 */
@Service
@RequiredArgsConstructor
@Qualifier("userDetailsServiceImpl")
public class UserService implements UserDetailsService {

    /**
     * The user repository to interact with users in the database.
     */
    private final UserRepository userRepository;

    /**
     * The password encoder to encode and decode passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * The user response mapper to map user entities to user response DTOs.
     */
    private final UserResponseMapper userResponseMapper;

    /**
     * Load a user by their username (email) from the database.
     *
     * @param username the username (email) of the user to load
     * @return the user with the given username
     * @throws UsernameNotFoundException if the user with the given username is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user.not.found"));
    }

    /**
     * Change the password of the currently logged-in user.
     *
     * @param request   the request containing the current password, new password, and confirm password
     * @param principal the principal of the currently logged-in user
     */
    @Transactional
    public void changePassword(ChangePasswordRequest request, Principal principal) {
        // Check if the new password and confirm password match
        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            throw new ChangePasswordException("error.password.match");
        }

        var user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        // Check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new ChangePasswordException("error.current.password");
        }

        // Update and save the user with the new password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    /**
     * Get the currently authenticated user.
     *
     * @param principal the principal of the currently authenticated user
     * @return the currently authenticated user
     */
    public UserResponse getAuthenticatedUser(Principal principal) {
        var user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return userResponseMapper.apply(user);
    }

    /**
     * Update the details of the currently authenticated user.
     *
     * @param userResponse the request containing the updated details
     * @param principal    the principal of the currently authenticated user
     * @return the updated user
     */
    public UserResponse updateUser(UserRequest userResponse, Principal principal) {
        var user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        userRepository.save(user);
        return userResponseMapper.apply(user);
    }

    /**
     * Delete the currently authenticated user.
     *
     * @param principal the principal of the currently authenticated user
     */
    public void deleteUser(Principal principal) {
        var user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        user.setVineyard(null);
        userRepository.delete(user);
    }

    /**
     * Get a user by their id.
     *
     * @param id the id of the user to get
     * @return the user with the given id
     */
    @PreAuthorize("hasAuthority('admin:read')")
    public UserResponse getUser(Integer id) {
        return userResponseMapper.apply(findUserById(id));
    }

    /**
     * Get all users.
     *
     * @param pageable object defining pagination info
     * @return the user response objects
     */
    @PreAuthorize("hasAuthority('admin:read')")
    public Page<UserResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userResponseMapper);
    }

    /**
     * Delete user by id provided
     *
     * @param userId user id to be deleted
     */
    @PreAuthorize("hasAuthority('admin:delete')")
    public void deleteUserById(Integer userId) {
        userRepository.delete(findUserById(userId));
    }

    /**
     * Block user with given request id
     *
     * @param request introduces user to be blocked details
     */
    @PreAuthorize("hasAuthority('admin:block')")
    public void blockUser(UserBlockRequest request) {
        User user = findUserById(request.getUserId());
        user.setBlocked(true);
        userRepository.save(user);
    }

    /**
     * Unblock user with given request id
     *
     * @param request introduces user to be unblocked details
     */
    @PreAuthorize("hasAuthority('admin:block')")
    public void unblockUser(UserBlockRequest request) {
        User user = findUserById(request.getUserId());
        user.setBlocked(false);
        userRepository.save(user);
    }

    /**
     * Find a user by their email.
     *
     * @param email the email of the user to find
     * @return the user with the given email
     * @throws UsernameNotFoundException if the user with the given email is not found
     */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("error.email.not.found"));
    }

    /**
     * Find a user by their id.
     *
     * @param id the id of the user to find
     * @return the user with the given id
     * @throws UsernameNotFoundException if the user with the given id is not found
     */
    public User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user.not.found"));
    }

    /**
     * Find all users working in a vineyard with the given id.
     *
     * @param vineyardId the id of the vineyard
     * @return list of users
     */
    public Page<User> getAllUsersByVineyardId(Integer vineyardId, Pageable pageable) {
        return userRepository.findAllByVineyard_Id(vineyardId, pageable);
    }

    /**
     * Save a user to the database.
     *
     * @param user the user to save
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Set the role of all staff in the vineyard with the given id to USER.
     * Break the association of the staff with the vineyard.
     *
     * @param vineyardId the id of the vineyard
     */
    public void disassociateVineyardStaff(Integer vineyardId) {
        List<User> vineyardStaff = userRepository.findAllByVineyard_Id(vineyardId);
        for (User user : vineyardStaff) {
            user.setRole(Role.USER);
            user.setVineyard(null);
        }
        userRepository.saveAll(vineyardStaff);
    }
}
