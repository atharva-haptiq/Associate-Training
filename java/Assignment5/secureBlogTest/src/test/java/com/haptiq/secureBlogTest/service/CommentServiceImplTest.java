package com.haptiq.secureBlogTest.service;

import com.haptiq.secureBlogTest.entity.*;
import com.haptiq.secureBlogTest.globalResponse.ApiResponse;
import com.haptiq.secureBlogTest.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock private CommentRepository commentRepository;
    @Mock private UserRepository userRepository;
    @Mock private BlogRepository blogRepository;
    @InjectMocks private CommentServiceImpl commentService;

    private User user;
    private Blog blog;
    private Comment comment;

    @BeforeEach
    void setUp() {
        user = new User(); user.setId(1L); user.setEmail("test@example.com");
        blog = new Blog(); blog.setId(1L); blog.setAuthor(user);
        comment = new Comment(); comment.setId(1L); comment.setUser(user); comment.setBlog(blog); comment.setComment("Nice post");
    }

    @Test
    void addComment_blogOrUserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = commentService.addComment("test@example.com", "test comment", 1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addComment_success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        ResponseEntity<?> response = commentService.addComment("test@example.com", "test comment", 1L);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void deleteComment_notFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = commentService.deleteComment(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteComment_success() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        ResponseEntity<?> response = commentService.deleteComment(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(commentRepository).deleteById(1L);
    }

    @Test
    void getCommentsByBlog_blogNotFound() {
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = commentService.getCommentsByBlog(1L, 0, 5);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getCommentsByBlog_success() {
        Page<Comment> page = new PageImpl<>(List.of(comment));
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
        when(commentRepository.findByBlog(blog, PageRequest.of(0, 5))).thenReturn(page);

        ResponseEntity<?> response = commentService.getCommentsByBlog(1L, 0, 5);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getCommentByUser_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = commentService.getCommentByUser(1L, 0, 5);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getCommentByUser_success() {
        Page<Comment> page = new PageImpl<>(List.of(comment));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.findByUser(user, PageRequest.of(0, 5))).thenReturn(page);

        ResponseEntity<?> response = commentService.getCommentByUser(1L, 0, 5);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
