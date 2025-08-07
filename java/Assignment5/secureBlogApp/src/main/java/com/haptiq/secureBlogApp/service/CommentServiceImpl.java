package com.haptiq.secureBlogApp.service;


import com.haptiq.secureBlogApp.entity.Blog;
import com.haptiq.secureBlogApp.entity.Comment;
import com.haptiq.secureBlogApp.entity.User;
import com.haptiq.secureBlogApp.repository.BlogRepository;
import com.haptiq.secureBlogApp.repository.CommentRepository;
import com.haptiq.secureBlogApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public ResponseEntity<?> addComment(Long userID, String comment, Long blogID) {
        User user = userRepository.findById(userID).orElse(null);
        Blog blog = blogRepository.findById(blogID).orElse(null);
        if (blog == null || user == null) return ResponseEntity.notFound().build();

        Comment commentObj = new Comment();
        commentObj.setBlog(blog);
        commentObj.setUser(user);
        commentObj.setComment(comment);
        Comment savedComment = commentRepository.save(commentObj);
        if (savedComment == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok("Comment Added successfully");

    }

    @Override
    public ResponseEntity<?> deleteComment(Long commentID) {
        Comment comment = commentRepository.findById(commentID).orElse(null);
        if (comment == null) return ResponseEntity.notFound().build();
        commentRepository.deleteById(commentID);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    @Override
    public ResponseEntity<?> getCommentsByBlog(Long blogID,int pageNumber, int pageSize) {
        Blog blog = blogRepository.findById(blogID).orElse(null);
        if (blog == null) return ResponseEntity.notFound().build();
        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize);
        Page<Comment> commentPage = commentRepository.findByBlog(blog,pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("content: ", commentPage.getContent());
        response.put("total elemnets: ", commentPage.getTotalElements());
        response.put("hasNext: ", commentPage.hasNext());

        if (response.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getCommentByUser(Long userID,int pageNumber, int pageSize) {
        User user = userRepository.findById(userID).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Comment> commentPage = commentRepository.findByUser(user, pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("content: ", commentPage.getContent());
        response.put("total elemnets: ", commentPage.getTotalElements());
        response.put("hasNext: ", commentPage.hasNext());

        if (response.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }
}
