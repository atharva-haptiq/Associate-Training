package com.haptiq.secureBlogApp.service;

import com.haptiq.secureBlogApp.entity.Blog;
import com.haptiq.secureBlogApp.entity.Comment;
import com.haptiq.secureBlogApp.entity.User;
import com.haptiq.secureBlogApp.globalResponse.ApiResponse;
import com.haptiq.secureBlogApp.repository.BlogRepository;
import com.haptiq.secureBlogApp.repository.CommentRepository;
import com.haptiq.secureBlogApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public ResponseEntity<?> addComment(String email, String comment, Long blogID) {
        User user = userRepository.findByEmail(email).orElse(null);
        Blog blog = blogRepository.findById(blogID).orElse(null);
        if (blog == null || user == null) {
            return new ResponseEntity<>(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "User or Blog not found", null), HttpStatus.NOT_FOUND);
        }

        Comment commentObj = new Comment();
        commentObj.setBlog(blog);
        commentObj.setUser(user);
        commentObj.setComment(comment);
        Comment savedComment = commentRepository.save(commentObj);

        return new ResponseEntity<>(new ApiResponse<>(true, HttpStatus.CREATED, "Comment added successfully", savedComment), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteComment(Long commentID) {
        Comment comment = commentRepository.findById(commentID).orElse(null);
        if (comment == null) {
            return new ResponseEntity<>(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "Comment not found", null), HttpStatus.NOT_FOUND);
        }
        commentRepository.deleteById(commentID);
        return new ResponseEntity<>(new ApiResponse<>(true, HttpStatus.OK, "Comment deleted successfully", null), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCommentsByBlog(Long blogID, int pageNumber, int pageSize) {
        Blog blog = blogRepository.findById(blogID).orElse(null);
        if (blog == null) {
            return new ResponseEntity<>(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "Blog not found", null), HttpStatus.NOT_FOUND);
        }
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Comment> commentPage = commentRepository.findByBlog(blog, pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("content", commentPage.getContent());
        response.put("totalElements", commentPage.getTotalElements());
        response.put("hasNext", commentPage.hasNext());

        return new ResponseEntity<>(new ApiResponse<>(true, HttpStatus.OK, "Comments fetched successfully", response), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCommentByUser(Long userID, int pageNumber, int pageSize) {
        User user = userRepository.findById(userID).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "User not found", null), HttpStatus.NOT_FOUND);
        }
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Comment> commentPage = commentRepository.findByUser(user, pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("content", commentPage.getContent());
        response.put("totalElements", commentPage.getTotalElements());
        response.put("hasNext", commentPage.hasNext());

        return new ResponseEntity<>(new ApiResponse<>(true, HttpStatus.OK, "Comments fetched successfully", response), HttpStatus.OK);
    }
}