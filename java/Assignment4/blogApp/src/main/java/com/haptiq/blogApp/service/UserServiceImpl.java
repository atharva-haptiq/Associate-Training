package com.haptiq.blogApp.service;

import com.haptiq.blogApp.dto.UserDTO;
import com.haptiq.blogApp.entity.User;
import com.haptiq.blogApp.enums.Role;
import com.haptiq.blogApp.globalResponse.ApiResponse;
import com.haptiq.blogApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse<?> registerUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setPassword(userDTO.getPassword());
        user.setRole(Role.USER);
        user.setBio(userDTO.getBio());
        User user1 = userRepository.save(user);
        if (user1.getId() != null) return new ApiResponse<>(true, HttpStatus.CREATED, "User Registered Successfully!",user1);
        else return new ApiResponse<>(false, HttpStatus.BAD_REQUEST, "User failed to register!",null);
    }

    @Override
    public ApiResponse<?> loginUser(String email, String password) {
        return null;
    }

    @Override
    public ApiResponse<?> getUserByID(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return new ApiResponse<>(false, HttpStatus.NOT_FOUND, "User not found", null);
        else return new ApiResponse<>(true, HttpStatus.OK, "User details: ", user);
    }

    @Override
    public ApiResponse<?> getUserByUsername(String username) {
        User user = userRepository.findByEmail(username).orElse(null);
        if(user == null) return new ApiResponse<>(false, HttpStatus.NOT_FOUND, "User not found",null);
        else return new ApiResponse<>(true, HttpStatus.OK,"User details:",user);
    }

    @Override
    public ApiResponse<?> changePassword(String email, String password) {
        return null;
    }
}
