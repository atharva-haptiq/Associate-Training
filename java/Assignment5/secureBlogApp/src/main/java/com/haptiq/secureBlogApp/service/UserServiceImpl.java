package com.haptiq.secureBlogApp.service;


import com.haptiq.secureBlogApp.dto.UserDTO;
import com.haptiq.secureBlogApp.entity.User;
import com.haptiq.secureBlogApp.enums.Role;
import com.haptiq.secureBlogApp.globalResponse.ApiResponse;
import com.haptiq.secureBlogApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<?> registerUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        user.setBio(userDTO.getBio());
        user.setValidated(true);
        User user1 = userRepository.save(user);
        if (user1.getId() != null) return new ApiResponse<>(true, HttpStatus.CREATED, "User Registered Successfully!",user1);
        else return new ApiResponse<>(false, HttpStatus.BAD_REQUEST, "User failed to register!",null);
    }

    @Override
    public boolean authenticate(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.isValidated()) {
                return false;
            }
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
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
