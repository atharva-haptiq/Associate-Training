package com.haptiq.blogApp.service;

import com.haptiq.blogApp.entity.Blog;
import com.haptiq.blogApp.entity.Comment;
import com.haptiq.blogApp.entity.User;
import com.haptiq.blogApp.repository.BlogRepository;
import com.haptiq.blogApp.repository.CommentRepository;
import com.haptiq.blogApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ResponseEntity<?> getCommentsByBlog(Long blogID) {
        Blog blog = blogRepository.findById(blogID).orElse(null);
        if (blog == null) return ResponseEntity.notFound().build();
        List<Comment> comments = commentRepository.findByBlog(blog);
        if (comments.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(comments);
    }

    @Override
    public ResponseEntity<?> getCommentByUser(Long userID) {
        User user = userRepository.findById(userID).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        List<Comment> comments = commentRepository.findByUser(user);
        if (comments.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(comments);
    }
}
