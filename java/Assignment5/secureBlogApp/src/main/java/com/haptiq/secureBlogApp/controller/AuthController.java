package com.haptiq.secureBlogApp.controller;

import com.haptiq.secureBlogApp.dto.UserDTO;
import com.haptiq.secureBlogApp.globalResponse.ApiResponse;
import com.haptiq.secureBlogApp.security.JwtUtils;
import com.haptiq.secureBlogApp.security.LoginRequest;
import com.haptiq.secureBlogApp.security.LoginResponse;
import com.haptiq.secureBlogApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ApiResponse<?> loginUser(@RequestBody LoginRequest loginRequest) {

        System.out.println("......................"+loginRequest);
        boolean isAuthenticated = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (isAuthenticated) {
            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String jwtToken = jwtUtils.generateToken(userDetails);
                return new ApiResponse<>(true, HttpStatus.ACCEPTED,"Login successful", new LoginResponse(userDetails.getUsername(), jwtToken));
            } catch (AuthenticationException ex) {
                return new ApiResponse<>(false, HttpStatus.BAD_REQUEST,"Bad credentials", null);
            }
        } else return new ApiResponse<>(false, HttpStatus.BAD_REQUEST,"Bad credentials", null);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO){
        return userService.registerUser(userDTO);
    }
}
