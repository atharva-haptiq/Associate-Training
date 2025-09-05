package com.haptiq.blogApp.service;

import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<?> addComment(Long userID, String comment, Long blogId);
    ResponseEntity<?> deleteComment(Long commentID);
    ResponseEntity<?> getCommentsByBlog(Long blogID, int pageNumber, int pageSize);
    ResponseEntity<?> getCommentByUser(Long userID,int pageNumber, int pageSize);
}
