package com.haptiq.secureBlogTest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haptiq.secureBlogTest.dto.BlogDTO;
import com.haptiq.secureBlogTest.dto.UserDTO;
import com.haptiq.secureBlogTest.globalResponse.ApiResponse;
import com.haptiq.secureBlogTest.security.JwtUtils;
import com.haptiq.secureBlogTest.security.LoginRequest;
import com.haptiq.secureBlogTest.security.LoginResponse;
import com.haptiq.secureBlogTest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Authentication authentication;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginUser_successful() {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");

        when(userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword()))
                .thenReturn(true);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        User userDetails = new User("user@example.com", "password", new java.util.ArrayList<>());
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(jwtUtils.generateToken(userDetails)).thenReturn("mocked-jwt-token");

        ApiResponse<?> response = authController.loginUser(loginRequest);

        assertTrue(response.isSuccess());
        assertEquals(HttpStatus.ACCEPTED, response.getHttpStatus());
        assertEquals("Login successful", response.getMessage());
        LoginResponse loginResp = (LoginResponse) response.getData();
        assertEquals("user@example.com", loginResp.getUsername());
        assertEquals("mocked-jwt-token", loginResp.getJwtToken());
    }

    @Test
    void loginUser_badCredentials_fromUserService() {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "wrongpassword");

        when(userService.authenticate(anyString(), anyString()))
                .thenReturn(false);

        ApiResponse<?> response = authController.loginUser(loginRequest);

        assertFalse(response.isSuccess());
        assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
        assertEquals("Bad credentials", response.getMessage());
    }

    @Test
    void loginUser_authenticationFails() {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");

        when(userService.authenticate(anyString(), anyString())).thenReturn(true);

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        ApiResponse<?> response = authController.loginUser(loginRequest);

        assertFalse(response.isSuccess());
        assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
        assertEquals("Bad credentials", response.getMessage());
    }
}
