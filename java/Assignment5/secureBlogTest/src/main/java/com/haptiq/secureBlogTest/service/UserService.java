    package com.haptiq.secureBlogTest.service;


    import com.haptiq.secureBlogTest.dto.UserDTO;
    import com.haptiq.secureBlogTest.globalResponse.ApiResponse;
    import jakarta.validation.Valid;
    import org.springframework.http.ResponseEntity;

    public interface UserService {
        ResponseEntity<?> registerUser(com.haptiq.secureBlogTest.dto.@Valid UserDTO userDTO);
        ResponseEntity<?> getUserByID(Long userId);
        ResponseEntity<?> getUserByUsername(String username);
        ResponseEntity<?> changePassword(String email, String password);
        public boolean authenticate(String email, String rawPassword);
    }
