package com.haptiq.secureBlogTest.service;

import com.haptiq.secureBlogTest.dto.UserDTO;
import com.haptiq.secureBlogTest.entity.User;
import com.haptiq.secureBlogTest.enums.Role;
import com.haptiq.secureBlogTest.globalResponse.ApiResponse;
import com.haptiq.secureBlogTest.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> registerUser(com.haptiq.secureBlogTest.dto.@Valid UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.USER);
        user.setBio(userDTO.getBio());
        user.setValidated(true);

        User savedUser = userRepository.save(user);

        if (savedUser.getId() != null) {
            return new ResponseEntity<>(
                    new ApiResponse<>(true, HttpStatus.CREATED, "User Registered Successfully!", savedUser),
                    HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, HttpStatus.BAD_REQUEST, "User registration failed!", null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }


    @Override
    public boolean authenticate(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.isValidated() && passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getUserByID(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "User not found", null));
        }
        return ResponseEntity
                .ok(new ApiResponse<>(true, HttpStatus.OK, "User details", user));
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getUserByUsername(String username) {
        User user = userRepository.findByEmail(username).orElse(null);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "User not found", null));
        }
        return ResponseEntity
                .ok(new ApiResponse<>(true, HttpStatus.OK, "User details", user));
    }

    @Override
    public ResponseEntity<ApiResponse<?>> changePassword(String email, String password) {
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body(new ApiResponse<>(false, HttpStatus.NOT_IMPLEMENTED, "Change password not yet implemented", null));
    }

}
