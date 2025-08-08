//package com.haptiq.secureBlogApp.service;
//
//import com.haptiq.secureBlogApp.dto.BlogDTO;
//import com.haptiq.secureBlogApp.entity.Blog;
//import com.haptiq.secureBlogApp.entity.User;
//import com.haptiq.secureBlogApp.repository.BlogRepository;
//import com.haptiq.secureBlogApp.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.*;
//import org.springframework.http.ResponseEntity;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class BlogServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private BlogRepository blogRepository;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private BlogServiceImpl blogService;
//
//    @Test
//    void createBlog_whenUserExists_thenSuccess() {
//        BlogDTO dto = new BlogDTO();
//        dto.setTitle("Title");
//        dto.setDescription("Desc");
//        dto.setContent("Content");
//        dto.setAuthorFirstName("john@example.com");
//
//        User user = new User();
//        user.setFirstName("John");
//
//        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
//        when(blogRepository.save(any(Blog.class))).thenReturn(new Blog());
//
//        ResponseEntity<?> response = blogService.createBlog(dto);
//        assertEquals(201, response.getStatusCodeValue());
//        assertEquals("Blog added successfully", response.getBody());
//    }
//
//    @Test
//    void createBlog_whenUserNotFound_then404() {
//        BlogDTO dto = new BlogDTO();
//        dto.setAuthorFirstName("unknown@example.com");
//
//        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = blogService.createBlog(dto);
//        assertEquals(404, response.getStatusCodeValue());
//        assertEquals("User not found", response.getBody());
//    }
//
//    @Test
//    void updateBlog_whenSuccess_then200() {
//        BlogDTO dto = new BlogDTO();
//        dto.setTitle("Updated Title");
//        dto.setDescription("Updated Desc");
//        dto.setContent("Updated Content");
//        dto.setAuthorFirstName("John");
//
//        User user = new User();
//        user.setFirstName("John");
//
//        Blog blog = new Blog();
//        blog.setId(1L);
//
//        when(userRepository.findByFirstName("John")).thenReturn(Optional.of(user));
//        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
//        when(blogRepository.save(any(Blog.class))).thenReturn(blog);
//
//        ResponseEntity<?> response = blogService.updateBlog(dto, 1L);
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("Blog updated successfully", response.getBody());
//    }
//
//    @Test
//    void updateBlog_whenUserMissing_then404() {
//        BlogDTO dto = new BlogDTO();
//        dto.setAuthorFirstName("Missing");
//
//        when(userRepository.findByFirstName("Missing")).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = blogService.updateBlog(dto, 1L);
//        assertEquals(404, response.getStatusCodeValue());
//        assertEquals("User not found", response.getBody());
//    }
//
//    @Test
//    void updateBlog_whenBlogMissing_then404() {
//        BlogDTO dto = new BlogDTO();
//        dto.setAuthorFirstName("John");
//
//        User user = new User();
//
//        when(userRepository.findByFirstName("John")).thenReturn(Optional.of(user));
//        when(blogRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = blogService.updateBlog(dto, 1L);
//        assertEquals(404, response.getStatusCodeValue());
//        assertEquals("Blog not found", response.getBody());
//    }
//
//    @Test
//    void deleteBlog_whenExists_then200() {
//        Blog blog = new Blog();
//        blog.setId(1L);
//
//        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
//
//        ResponseEntity<?> response = blogService.deleteBlog(1L);
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("Blog deleted successfully", response.getBody());
//        verify(blogRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void deleteBlog_whenNotFound_then404() {
//        when(blogRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = blogService.deleteBlog(1L);
//        assertEquals(404, response.getStatusCodeValue());
//        assertEquals("Blog not found", response.getBody());
//    }
//
//    @Test
//    void getBlogById_whenFound_then200() {
//        Blog blog = new Blog();
//        blog.setId(1L);
//
//        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
//
//        ResponseEntity<?> response = blogService.getBlogById(1L);
//        assertEquals(200, response.getStatusCodeValue());
//        assertSame(blog, response.getBody());
//    }
//
//    @Test
//    void getBlogById_whenNotFound_then404() {
//        when(blogRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = blogService.getBlogById(1L);
//        assertEquals(404, response.getStatusCodeValue());
//        assertEquals("Blog not found", response.getBody());
//    }
//
//    @Test
//    void getAllBlogs_returnsPagedData() {
//        Blog blog = new Blog();
//        List<Blog> blogList = List.of(blog);
//        Page<Blog> page = new PageImpl<>(blogList);
//
//        when(blogRepository.findAll(any(PageRequest.class))).thenReturn(page);
//
//        ResponseEntity<?> response = blogService.getAllBlogs(0, 10);
//        assertEquals(200, response.getStatusCodeValue());
//
//        Map<?, ?> body = (Map<?, ?>) response.getBody();
//        assertEquals(blogList, body.get("content"));
//        assertEquals(1L, body.get("totalElements"));
//        assertEquals(false, body.get("hasNext"));
//    }
//
//    @Test
//    void getBlogByAuthor_whenFound_thenReturnPaged() {
//        User author = new User();
//        Blog blog = new Blog();
//        List<Blog> blogs = List.of(blog);
//        Page<Blog> page = new PageImpl<>(blogs);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(author));
//        when(blogRepository.findByAuthor(eq(author), any(PageRequest.class))).thenReturn(page);
//
//        ResponseEntity<?> response = blogService.getBlogByAuthor(1L, 0, 5);
//        assertEquals(200, response.getStatusCodeValue());
//
//        Map<?, ?> body = (Map<?, ?>) response.getBody();
//        assertEquals(blogs, body.get("content"));
//        assertEquals(1L, body.get("totalElements"));
//    }
//
//    @Test
//    void getBlogByAuthor_whenUserMissing_then404() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = blogService.getBlogByAuthor(1L, 0, 5);
//        assertEquals(404, response.getStatusCodeValue());
//        assertEquals("Author not found", response.getBody());
//    }
//}
