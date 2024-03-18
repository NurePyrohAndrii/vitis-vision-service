package com.vitisvision.vitisvisionservice.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
}