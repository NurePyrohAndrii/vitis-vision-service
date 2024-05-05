package com.vitisvision.vitisvisionservice.user.service;

import com.vitisvision.vitisvisionservice.user.dto.ChangePasswordRequest;
import com.vitisvision.vitisvisionservice.user.exception.ChangePasswordException;
import com.vitisvision.vitisvisionservice.user.entity.User;
import com.vitisvision.vitisvisionservice.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private String currentPasswordEncoded;
    private UsernamePasswordAuthenticationToken authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        String email = "johndoe@gmail.com";
        currentPasswordEncoded = "encodedPassword";
        testUser = User.builder()
                .email(email)
                .password(currentPasswordEncoded)
                .build();

        authentication = new UsernamePasswordAuthenticationToken(testUser, null, Collections.singletonList(new SimpleGrantedAuthority("USER")));

        // Mock
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(passwordEncoder.matches(anyString(), anyString())).thenAnswer(invocation -> {
            String rawPassword = invocation.getArgument(0);
            String encodedPassword = invocation.getArgument(1);
            return rawPassword.equals(encodedPassword);
        });
    }

    @Test
    void testLoadUserByUsername_UserExists_ReturnsUserDetails() {
        // Given
        String email = "johndoe@gmail.com";
        User testUser = User.builder()
                .email(email)
                .build();

        // Mock
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));

        // When
        User result = (User) userService.loadUserByUsername(email);

        // Then
        assertNotNull(result);
        assertEquals(email, testUser.getEmail());
    }

    @Test
    void testLoadUserByUsername_UserNotExists_ThrowsUsernameNotFoundException() {
        // Given
        String email = "johndoe@gmail.com";

        // Mock
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
    }

    @Test
    void changePassword_Successful() {
        // Given
        String newPassword = "NewPassword1!";
        ChangePasswordRequest request = new ChangePasswordRequest(currentPasswordEncoded, newPassword, newPassword);

        // When
        userService.changePassword(request, authentication);

        // Then
        verify(userRepository, times(1)).save(testUser);
        assertEquals(newPassword, testUser.getPassword());
    }

    @Test
    void changePassword_PasswordMismatch_ThrowsException() {
        ChangePasswordRequest request = new ChangePasswordRequest(currentPasswordEncoded, "NewPassword1!", "Mismatch1!");

        assertThrows(ChangePasswordException.class, () -> userService.changePassword(request, authentication));
    }

    @Test
    void changePassword_IncorrectCurrentPassword_ThrowsException() {
        ChangePasswordRequest request = new ChangePasswordRequest("WrongCurrentPassword", "NewPassword1!", "NewPassword1!");

        assertThrows(ChangePasswordException.class, () -> userService.changePassword(request, authentication));
    }

}