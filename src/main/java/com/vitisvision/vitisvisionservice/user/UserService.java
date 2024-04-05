package com.vitisvision.vitisvisionservice.user;

import com.vitisvision.vitisvisionservice.exception.ChangePasswordException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
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
            throw new ChangePasswordException("New password and confirm password do not match");
        }

        var user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        // Check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new ChangePasswordException("Current password is incorrect");
        }

        // Update and save the user with the new password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
