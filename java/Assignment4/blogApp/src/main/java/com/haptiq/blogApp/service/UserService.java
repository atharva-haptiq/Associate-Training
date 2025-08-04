package com.haptiq.blogApp.service;

import com.haptiq.blogApp.dto.UserDTO;
import com.haptiq.blogApp.globalResponse.ApiResponse;

public interface UserService {
    ApiResponse<?> registerUser(UserDTO userDTO);
    ApiResponse<?> loginUser(String email, String password);
    ApiResponse<?> getUserByID(Long userId);
    ApiResponse<?> getUserByUsername(String username);
    ApiResponse<?> changePassword(String email, String password);
}
