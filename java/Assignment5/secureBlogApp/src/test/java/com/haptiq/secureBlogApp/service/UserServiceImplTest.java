
package com.haptiq.secureBlogApp.service;

import com.haptiq.secureBlogApp.dto.UserDTO;
import com.haptiq.secureBlogApp.entity.User;
import com.haptiq.secureBlogApp.enums.Role;
import com.haptiq.secureBlogApp.globalResponse.ApiResponse;
import com.haptiq.secureBlogApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setMobile("1234567890");
        userDTO.setPassword("password123");
        userDTO.setBio("Bio");

        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setMobile("1234567890");
        user.setPassword("encodedPassword");
        user.setRole(Role.USER);
        user.setBio("Bio");
        user.setValidated(true);
    }

    @Test
    public void testRegisterUser_Success() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = userService.registerUser(userDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertNotNull(apiResponse);
        assertTrue(apiResponse.isSuccess());
        assertEquals("User Registered Successfully!", apiResponse.getMessage());
        assertEquals(user.getEmail(), ((User) apiResponse.getData()).getEmail());
    }

    @Test
    public void testRegisterUser_Failure() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        User failedUser = new User(); // ID will be null, simulating failure
        when(userRepository.save(any(User.class))).thenReturn(failedUser);

        ResponseEntity<?> response = userService.registerUser(userDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();
        assertNotNull(apiResponse);
        assertFalse(apiResponse.isSuccess());
        assertEquals("User registration failed!", apiResponse.getMessage());
    }

    @Test
    public void testAuthenticate_ValidCredentials() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        boolean result = userService.authenticate("john@example.com", "password123");
        assertTrue(result);
    }

    @Test
    public void testAuthenticate_InvalidCredentials() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        boolean result = userService.authenticate("john@example.com", "wrongPassword");
        assertFalse(result);
    }

    @Test
    public void testAuthenticate_UserNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        boolean result = userService.authenticate("unknown@example.com", "password123");
        assertFalse(result);
    }

    @Test
    public void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<ApiResponse<?>> response = userService.getUserByID(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("User details", response.getBody().getMessage());
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<?>> response = userService.getUserByID(2L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("User not found", response.getBody().getMessage());
    }

    @Test
    public void testGetUserByUsername_Found() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        ResponseEntity<ApiResponse<?>> response = userService.getUserByUsername("john@example.com");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("User details", response.getBody().getMessage());
    }

    @Test
    public void testGetUserByUsername_NotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<?>> response = userService.getUserByUsername("unknown@example.com");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("User not found", response.getBody().getMessage());
    }

    @Test
    public void testChangePassword_NotImplemented() {
        ResponseEntity<ApiResponse<?>> response = userService.changePassword("john@example.com", "newPassword");
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Change password not yet implemented", response.getBody().getMessage());
    }
}