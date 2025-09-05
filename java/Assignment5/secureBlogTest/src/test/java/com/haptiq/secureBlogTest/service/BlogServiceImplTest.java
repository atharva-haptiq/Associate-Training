package com.haptiq.secureBlogTest.service;
import com.haptiq.secureBlogTest.dto.BlogDTO;
import com.haptiq.secureBlogTest.entity.Blog;
import com.haptiq.secureBlogTest.entity.User;
import com.haptiq.secureBlogTest.globalResponse.ApiResponse;
import com.haptiq.secureBlogTest.repository.BlogRepository;
import com.haptiq.secureBlogTest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogServiceImplTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private BlogServiceImpl blogService;

    private User user;
    private Blog blog;
    private BlogDTO blogDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        blog = new Blog();
        blog.setId(1L);
        blog.setTitle("Sample Title");
        blog.setDescription("Sample Description");
        blog.setContent("Sample Content");
        blog.setAuthor(user);

        blogDTO = new BlogDTO();
        blogDTO.setTitle("Sample Title");
        blogDTO.setDescription("Sample Description");
        blogDTO.setContent("Sample Content");
    }

    @Test
    void createBlog_userExists_blogSavedSuccessfully() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);

        ResponseEntity<ApiResponse<?>> response = blogService.createBlog(blogDTO, "test@example.com");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Blog added successfully", response.getBody().getMessage());
        verify(blogRepository).save(any(Blog.class));
    }

    @Test
    void createBlog_userNotFound_returnsNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<?>> response = blogService.createBlog(blogDTO, "unknown@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody().getMessage());
    }

    @Test
    void updateBlog_userAndBlogExist_updatesSuccessfully() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);

        ResponseEntity<ApiResponse<?>> response = blogService.updateBlog(blogDTO, 1L, "test@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Blog updated successfully", response.getBody().getMessage());
    }

    @Test
    void updateBlog_userNotFound_returnsNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<?>> response = blogService.updateBlog(blogDTO, 1L, "test@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody().getMessage());
    }

    @Test
    void updateBlog_blogNotFound_returnsNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<?>> response = blogService.updateBlog(blogDTO, 1L, "test@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Blog not found", response.getBody().getMessage());
    }

    @Test
    void deleteBlog_blogExists_deletesSuccessfully() {
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));

        ResponseEntity<ApiResponse<?>> response = blogService.deleteBlog(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Blog deleted successfully", response.getBody().getMessage());
        verify(blogRepository).deleteById(1L);
    }

    @Test
    void deleteBlog_blogNotFound_returnsNotFound() {
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<?>> response = blogService.deleteBlog(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Blog not found", response.getBody().getMessage());
    }

    @Test
    void getBlogById_blogFound_returnsSuccess() {
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));

        ResponseEntity<ApiResponse<?>> response = blogService.getBlogById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(blog, response.getBody().getData());
    }

    @Test
    void getBlogById_blogNotFound_returnsNotFound() {
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<?>> response = blogService.getBlogById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Blog not found", response.getBody().getMessage());
    }

    @Test
    void getAllBlogs_returnsPagedBlogsSuccessfully() {
        List<Blog> blogs = List.of(blog);
        Page<Blog> blogPage = new PageImpl<>(blogs);

        when(blogRepository.findAll(PageRequest.of(0, 10))).thenReturn(blogPage);

        ResponseEntity<ApiResponse<?>> response = blogService.getAllBlogs(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((Map<?, ?>) response.getBody().getData()).containsKey("content"));
    }

    @Test
    void getBlogByAuthor_authorExists_returnsPagedBlogs() {
        List<Blog> blogs = List.of(blog);
        Page<Blog> blogPage = new PageImpl<>(blogs);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(blogRepository.findByAuthor(user, PageRequest.of(0, 10))).thenReturn(blogPage);

        ResponseEntity<ApiResponse<?>> response = blogService.getBlogByAuthor(1L, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Blogs by author fetched successfully", response.getBody().getMessage());
    }

    @Test
    void getBlogByAuthor_authorNotFound_returnsNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<?>> response = blogService.getBlogByAuthor(1L, 0, 10);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Author not found", response.getBody().getMessage());
    }
}
