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
    public ResponseEntity<?> getCommentsByBlog(Long blogID,
                                               @RequestParam (required = false, defaultValue = "0") int pageNumber,
                                               @RequestParam (required = false, defaultValue = "10") int pageSize){
        return commentService.getCommentsByBlog(blogID,pageNumber,pageSize);
    }

    @GetMapping("/byUser")
    public ResponseEntity<?> getCommentByUser(Long userID,
                                              @RequestParam (required = false, defaultValue = "0") int pageNumber,
                                              @RequestParam (required = false, defaultValue = "10") int pageSize){
        return commentService.getCommentByUser(userID,pageNumber,pageSize);
    }
}

