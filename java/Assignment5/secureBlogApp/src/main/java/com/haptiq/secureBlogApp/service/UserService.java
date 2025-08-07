package com.haptiq.secureBlogApp.service;


import com.haptiq.secureBlogApp.dto.UserDTO;
import com.haptiq.secureBlogApp.globalResponse.ApiResponse;

public interface UserService {
    ApiResponse<?> registerUser(UserDTO userDTO);
    ApiResponse<?> loginUser(String email, String password);
    ApiResponse<?> getUserByID(Long userId);
    ApiResponse<?> getUserByUsername(String username);
    ApiResponse<?> changePassword(String email, String password);
    public boolean authenticate(String email, String rawPassword);
}
