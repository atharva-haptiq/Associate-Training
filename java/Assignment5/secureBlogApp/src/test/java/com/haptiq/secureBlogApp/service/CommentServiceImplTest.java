//package com.haptiq.secureBlogApp.service;
//
//import com.haptiq.secureBlogApp.entity.Blog;
//import com.haptiq.secureBlogApp.entity.Comment;
//import com.haptiq.secureBlogApp.entity.User;
//import com.haptiq.secureBlogApp.globalResponse.ApiResponse;
//import com.haptiq.secureBlogApp.repository.BlogRepository;
//import com.haptiq.secureBlogApp.repository.CommentRepository;
//import com.haptiq.secureBlogApp.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.data.domain.*;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class CommentServiceImplTest {
//
//    @InjectMocks
//    private CommentServiceImpl commentService;
//
//    @Mock
//    private CommentRepository commentRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private BlogRepository blogRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testAddComment_whenUserAndBlogExist_thenReturnsSuccess() {
//        Long userId = 1L, blogId = 1L;
//        String commentText = "Nice blog!";
//
//        User user = new User(); user.setId(userId);
//        Blog blog = new Blog(); blog.setId(blogId);
//
//        Comment comment = new Comment();
//        comment.setComment(commentText);
//        comment.setUser(user);
//        comment.setBlog(blog);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(blogRepository.findById(blogId)).thenReturn(Optional.of(blog));
//        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
//
//        ResponseEntity<?> response = commentService.addComment(userId, commentText, blogId);
//        ApiResponse<?> api = (ApiResponse<?>) response.getBody();
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNotNull(api);
//        assertTrue(api.isSuccess());
//        assertEquals("Comment added successfully", api.getMessage());
//        assertEquals(commentText, ((Comment) api.getData()).getComment());
//    }
//
//    @Test
//    void testAddComment_whenUserOrBlogNotFound_thenReturnsNotFound() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//        when(blogRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = commentService.addComment(1L, "Test", 1L);
//        ApiResponse<?> api = (ApiResponse<?>) response.getBody();
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertFalse(api.isSuccess());
//        assertEquals("User or Blog not found", api.getMessage());
//    }
//
//    @Test
//    void testDeleteComment_whenExists_thenReturnsSuccess() {
//        Long commentId = 1L;
//        Comment comment = new Comment(); comment.setId(commentId);
//
//        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
//
//        ResponseEntity<?> response = commentService.deleteComment(commentId);
//        ApiResponse<?> api = (ApiResponse<?>) response.getBody();
//
//        verify(commentRepository).deleteById(commentId);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(api.isSuccess());
//        assertEquals("Comment deleted successfully", api.getMessage());
//    }
//
//    @Test
//    void testDeleteComment_whenNotFound_thenReturnsNotFound() {
//        when(commentRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = commentService.deleteComment(1L);
//        ApiResponse<?> api = (ApiResponse<?>) response.getBody();
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertFalse(api.isSuccess());
//        assertEquals("Comment not found", api.getMessage());
//    }
//
//    @Test
//    void testGetCommentsByBlog_whenBlogExists_thenReturnsPage() {
//        Long blogId = 1L;
//        Blog blog = new Blog(); blog.setId(blogId);
//
//        Comment comment1 = new Comment(); comment1.setComment("c1");
//        Comment comment2 = new Comment(); comment2.setComment("c2");
//
//        List<Comment> comments = List.of(comment1, comment2);
//        Page<Comment> commentPage = new PageImpl<>(comments);
//
//        when(blogRepository.findById(blogId)).thenReturn(Optional.of(blog));
//        when(commentRepository.findByBlog(eq(blog), any(Pageable.class))).thenReturn(commentPage);
//
//        ResponseEntity<?> response = commentService.getCommentsByBlog(blogId, 0, 10);
//        ApiResponse<?> api = (ApiResponse<?>) response.getBody();
//        Map<String, Object> data = (Map<String, Object>) api.getData();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(api.isSuccess());
//        assertEquals("Comments fetched successfully", api.getMessage());
//        assertEquals(2L, ((List<?>) data.get("content")).size());
//    }
//
//    @Test
//    void testGetCommentsByBlog_whenBlogNotFound_thenReturnsNotFound() {
//        when(blogRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = commentService.getCommentsByBlog(1L, 0, 10);
//        ApiResponse<?> api = (ApiResponse<?>) response.getBody();
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertFalse(api.isSuccess());
//        assertEquals("Blog not found", api.getMessage());
//    }
//
//    @Test
//    void testGetCommentByUser_whenUserExists_thenReturnsPage() {
//        Long userId = 1L;
//        User user = new User(); user.setId(userId);
//
//        Comment comment = new Comment(); comment.setComment("Test comment");
//
//        Page<Comment> commentPage = new PageImpl<>(List.of(comment));
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(commentRepository.findByUser(eq(user), any(Pageable.class))).thenReturn(commentPage);
//
//        ResponseEntity<?> response = commentService.getCommentByUser(userId, 0, 10);
//        ApiResponse<?> api = (ApiResponse<?>) response.getBody();
//        Map<String, Object> data = (Map<String, Object>) api.getData();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(api.isSuccess());
//        assertEquals("Comments fetched successfully", api.getMessage());
//        assertEquals(1L, ((List<?>) data.get("content")).size());
//    }
//
//    @Test
//    void testGetCommentByUser_whenUserNotFound_thenReturnsNotFound() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = commentService.getCommentByUser(1L, 0, 10);
//        ApiResponse<?> api = (ApiResponse<?>) response.getBody();
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertFalse(api.isSuccess());
//        assertEquals("User not found", api.getMessage());
//    }
//}
