package com.haptiq.secureBlogApp.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class LoginRequest {
    @NotNull
    @NotBlank
    private String email;
    @NotBlank
    @NotNull
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" + "email='" + email + '\'' + ", password='" + password + '\'' + '}';
    }
}
