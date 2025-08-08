package com.haptiq.secureBlogTest.controller;

import com.haptiq.secureBlogTest.dto.BlogDTO;
import com.haptiq.secureBlogTest.security.JwtFilter;
import com.haptiq.secureBlogTest.security.JwtUtils;
import com.haptiq.secureBlogTest.service.BlogService;
import com.haptiq.secureBlogTest.service.CommentService;
import com.haptiq.secureBlogTest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private BlogService blogService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void testGetUserByID() throws Exception {
        mockMvc.perform(get("/user/byId")
                .param("userId", "1"));
    }

    @Test
    void testGetUserByUsername() throws Exception {
        mockMvc.perform(get("/user/byName")
                .param("userName", "john"));
    }

    @Test
    void testCreateBlog() throws Exception {
        String json = objectMapper.writeValueAsString(new BlogDTO());

        mockMvc.perform(post("/user/addBlog")
                .header("Authorization", "Bearer faketoken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @Test
    void testUpdateBlog() throws Exception {
        String json = objectMapper.writeValueAsString(new BlogDTO());

        mockMvc.perform(put("/user/updateBlog")
                .header("Authorization", "Bearer faketoken")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @Test
    void testDeleteBlog() throws Exception {
        mockMvc.perform(delete("/user/deleteBlog")
                .param("blogID", "1"));
    }

    @Test
    void testAddComment() throws Exception {
        mockMvc.perform(post("/user/addComment")
                .header("Authorization", "Bearer faketoken")
                .param("comment", "Nice post!")
                .param("blogId", "5"));
    }

    @Test
    void testDeleteComment() throws Exception {
        mockMvc.perform(delete("/user/deleteComment")
                .param("commentID", "10"));
    }
}
