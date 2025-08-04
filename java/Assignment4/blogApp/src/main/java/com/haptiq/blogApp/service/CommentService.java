package com.haptiq.blogApp.service;

import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<?> addComment(Long userID, String comment, Long blogId);
    ResponseEntity<?> deleteComment(Long commentID);
    ResponseEntity<?> getCommentsByBlog(Long blogID);
    ResponseEntity<?> getCommentByUser(Long userID);
}
