package com.haptiq.blogApp.controller;

import com.haptiq.blogApp.dto.UserDTO;
import com.haptiq.blogApp.globalResponse.ApiResponse;
import com.haptiq.blogApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @GetMapping("/byId")
    public ResponseEntity<ApiResponse<?>> getUserByID(@RequestParam Long userId){
        return ResponseEntity.ok(userService.getUserByID(userId));
    }

    @GetMapping("/byName")
    public ResponseEntity<ApiResponse<?>> getUserByUsername(@RequestParam String userName){
        return ResponseEntity.ok(userService.getUserByUsername(userName));
    }

}

