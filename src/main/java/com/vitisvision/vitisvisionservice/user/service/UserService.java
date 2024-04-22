package com.vitisvision.vitisvisionservice.user.service;

import com.vitisvision.vitisvisionservice.user.enumeration.Role;
import com.vitisvision.vitisvisionservice.user.repository.UserRepository;
import com.vitisvision.vitisvisionservice.user.dto.ChangePasswordRequest;
import com.vitisvision.vitisvisionservice.user.exception.ChangePasswordException;
import com.vitisvision.vitisvisionservice.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
     * @param request the request containing the current password, new password, and confirm password
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
     * Save a user to the database.
     *
     * @param user the user to save
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Set the role of all staff of the vineyard with the given id to USER.
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
