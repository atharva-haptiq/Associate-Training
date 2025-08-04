package com.haptiq.blogApp.controller;

import com.haptiq.blogApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestParam Long userID, @RequestParam String comment,@RequestParam Long blogId){
        return commentService.addComment(userID,comment,blogId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestParam Long commentID){
        return commentService.deleteComment(commentID);
    }
    @GetMapping("/byBlog")
    public ResponseEntity<?> getCommentsByBlog(Long blogID){
        return commentService.getCommentsByBlog(blogID);
    }

    @GetMapping("/byUser")
    public ResponseEntity<?> getCommentByUser(Long userID){
        return commentService.getCommentByUser(userID);
    }
}

