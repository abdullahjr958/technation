package com.technation.technation.service;

import static org.junit.jupiter.api.Assertions.*;

import com.technation.technation.dto.PasswordDTO;
import com.technation.technation.model.User;
import com.technation.technation.repository.UserRepository;
import com.technation.technation.model.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_ShouldEncodePasswordAndSetCreatedAt() {
        User user = new User();
        user.setPassword("rawPassword");

        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        userService.saveUser(user);

        assertEquals("encodedPassword", user.getPassword());
        assertNotNull(user.getCreatedAt());
        verify(userRepo).save(user);
    }

    @Test
    void getUserByEmail_ShouldReturnTrue_WhenUserExists() {
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        boolean result = userService.getUserByEmail("test@example.com");

        assertTrue(result);
    }

    @Test
    void getUserByEmail_ShouldReturnFalse_WhenUserNotFound() {
        when(userRepo.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        boolean result = userService.getUserByEmail("notfound@example.com");

        assertFalse(result);
    }

    @Test
    void getById_ShouldReturnUser_WhenFound() {
        User user = new User();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getById(1);

        assertEquals(user, result);
    }

    @Test
    void getById_ShouldReturnNull_WhenNotFound() {
        when(userRepo.findById(2)).thenReturn(Optional.empty());

        User result = userService.getById(2);

        assertNull(result);
    }

    @Test
    void checkPassword_ShouldReturnOk_WhenOldPasswordMatches() {
        // Arrange
        User user = new User();
        user.setPassword("encodedOldPassword");

        PasswordDTO dto = new PasswordDTO();
        dto.setOldPass("rawOldPassword");
        dto.setNewPass("newRawPassword");

        // Set up SecurityContext mock
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getPrincipal()).thenReturn(new CustomUserDetails(user));

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        when(passwordEncoder.matches("rawOldPassword", "encodedOldPassword")).thenReturn(true);
        when(passwordEncoder.encode("newRawPassword")).thenReturn("newEncodedPassword");

        // Act
        ResponseEntity<?> response = userService.checkPassword(dto);

        // Assert
        assertEquals("newEncodedPassword", user.getPassword());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Password Changed Successfully", response.getBody());
        verify(userRepo).save(user);
    }

    @Test
    void checkPassword_ShouldReturnBadRequest_WhenOldPasswordWrong() {
        // Arrange
        User user = new User();
        user.setPassword("encodedOldPassword");

        PasswordDTO dto = new PasswordDTO();
        dto.setOldPass("wrongPass");
        dto.setNewPass("newPass");

        // Mock SecurityContext
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getPrincipal()).thenReturn(new CustomUserDetails(user));

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        when(passwordEncoder.matches("wrongPass", "encodedOldPassword")).thenReturn(false);

        // Act
        ResponseEntity<?> response = userService.checkPassword(dto);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(((Map<?, ?>) response.getBody()).get("message").toString().contains("Wrong Password"));
        verify(userRepo, never()).save(any());
    }
}
