package com.haptiq.secureBlogApp.controller;

import com.haptiq.secureBlogApp.dto.BlogDTO;
import com.haptiq.secureBlogApp.dto.UserDTO;
import com.haptiq.secureBlogApp.globalResponse.ApiResponse;
import com.haptiq.secureBlogApp.security.JwtUtils;
import com.haptiq.secureBlogApp.service.BlogService;
import com.haptiq.secureBlogApp.service.CommentService;
import com.haptiq.secureBlogApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private JwtUtils jwtUtils;


    @GetMapping("/byId")
    public ResponseEntity<?> getUserByID(@RequestParam Long userId){
        return userService.getUserByID(userId);
    }

    @GetMapping("/byName")
    public ResponseEntity<?> getUserByUsername(@RequestParam String userName){
        return userService.getUserByUsername(userName);
    }

    @PostMapping("/addBlog")
    public ResponseEntity<?> createBlog(@RequestHeader ("Authorization") String token,@Valid @RequestBody BlogDTO blogDTO){
        token = token.substring(7);
        String email = jwtUtils.extractUsername(token);
        return blogService.createBlog(blogDTO,email);
    }

    @PutMapping("/updateBlog")
    public ResponseEntity<?> updateBlog(@RequestHeader ("Authorization") String token,
                                        @Valid @RequestBody BlogDTO blogDTO, @RequestParam Long id){
        token = token.substring(7);
        String email = jwtUtils.extractUsername(token);
        return blogService.updateBlog(blogDTO,id,email);
    }

    @DeleteMapping("/deleteBlog")
    public ResponseEntity<?> deleteBlog(@RequestParam Long blogID){
        return blogService.deleteBlog(blogID);
    }

    @PostMapping("/addComment")
    public ResponseEntity<?> addComment( @RequestHeader ("Authorization") String token,
                                         @RequestParam String comment,
                                         @RequestParam Long blogId){
        token = token.substring(7);
        String email = jwtUtils.extractUsername(token);
        return commentService.addComment(email,comment,blogId);
    }

    @DeleteMapping("/deleteComment")
    public ResponseEntity<?> deleteComment(@RequestParam Long commentID){
        return commentService.deleteComment(commentID);
    }

}

