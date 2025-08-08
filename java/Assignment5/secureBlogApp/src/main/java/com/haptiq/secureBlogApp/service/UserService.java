    package com.haptiq.secureBlogApp.service;


    import com.haptiq.secureBlogApp.dto.UserDTO;
    import com.haptiq.secureBlogApp.globalResponse.ApiResponse;
    import org.springframework.http.ResponseEntity;

    public interface UserService {
        ResponseEntity<?> registerUser(UserDTO userDTO);
        ResponseEntity<?> getUserByID(Long userId);
        ResponseEntity<?> getUserByUsername(String username);
        ResponseEntity<?> changePassword(String email, String password);
        public boolean authenticate(String email, String rawPassword);
    }
