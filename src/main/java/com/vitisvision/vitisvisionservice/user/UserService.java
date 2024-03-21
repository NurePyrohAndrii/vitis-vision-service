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

@Service
@RequiredArgsConstructor
@Qualifier("userDetailsServiceImpl")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
    }

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
