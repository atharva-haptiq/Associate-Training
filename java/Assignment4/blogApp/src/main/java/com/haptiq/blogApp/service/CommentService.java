package com.haptiq.blogApp.service;

import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<?> addComment(Long userID, String comment, Long commentID);
    ResponseEntity<?> deleteComment(Long commentID);
    ResponseEntity<?> getCommentsByBlog(Long blogID);
    ResponseEntity<?> getCommentByUser(Long userID);
}
