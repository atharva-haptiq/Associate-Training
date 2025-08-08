package com.haptiq.secureBlogTest.controller;
import com.haptiq.secureBlogTest.security.JwtFilter;
import com.haptiq.secureBlogTest.security.JwtUtils;
import com.haptiq.secureBlogTest.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void testGetCommentsByBlog_withParams() throws Exception {
        mockMvc.perform(get("/comment/byBlog")
                .param("blogID", "1")
                .param("pageNumber", "0")
                .param("pageSize", "5"));
    }

    @Test
    void testGetCommentsByBlog_defaults() throws Exception {
        mockMvc.perform(get("/comment/byBlog")
                .param("blogID", "1"));
    }

    @Test
    void testGetCommentByUser_withParams() throws Exception {
        mockMvc.perform(get("/comment/byUser")
                .param("userID", "10")
                .param("pageNumber", "2")
                .param("pageSize", "5"));
    }

    @Test
    void testGetCommentByUser_defaults() throws Exception {
        mockMvc.perform(get("/comment/byUser")
                .param("userID", "10"));
    }
}

