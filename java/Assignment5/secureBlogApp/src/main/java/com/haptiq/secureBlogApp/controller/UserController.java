package com.haptiq.secureBlogApp.controller;

import com.haptiq.secureBlogApp.dto.BlogDTO;
import com.haptiq.secureBlogApp.dto.UserDTO;
import com.haptiq.secureBlogApp.globalResponse.ApiResponse;
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
    @GetMapping("/byId")
    public ResponseEntity<ApiResponse<?>> getUserByID(@RequestParam Long userId){
        return ResponseEntity.ok(userService.getUserByID(userId));
    }

    @GetMapping("/byName")
    public ResponseEntity<ApiResponse<?>> getUserByUsername(@RequestParam String userName){
        return ResponseEntity.ok(userService.getUserByUsername(userName));
    }

    @PostMapping("/addBlog")
    public ResponseEntity<?> createBlog(@RequestHeader ("Authorization") String token,@Valid @RequestBody BlogDTO blogDTO){
        token = token.substring(7);
        return blogService.createBlog(blogDTO);
    }

    @PutMapping("/updateBlog")
    public ResponseEntity<?> updateBlog(@Valid @RequestBody BlogDTO blogDTO, @RequestParam Long id){
        return blogService.updateBlog(blogDTO,id);
    }

    @DeleteMapping("/deleteBlog")
    public ResponseEntity<?> deleteBlog(@RequestParam Long blogID){
        return blogService.deleteBlog(blogID);
    }

    @PostMapping("/addComment")
    public ResponseEntity<?> addComment(@RequestParam Long userID, @RequestParam String comment,@RequestParam Long blogId){
        return commentService.addComment(userID,comment,blogId);
    }

    @DeleteMapping("/deleteComment")
    public ResponseEntity<?> deleteComment(@RequestParam Long commentID){
        return commentService.deleteComment(commentID);
    }

}

