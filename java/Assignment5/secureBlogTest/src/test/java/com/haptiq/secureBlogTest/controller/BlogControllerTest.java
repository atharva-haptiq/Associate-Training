package com.haptiq.secureBlogTest.controller;

import com.haptiq.secureBlogTest.security.JwtFilter;
import com.haptiq.secureBlogTest.security.JwtUtils;
import com.haptiq.secureBlogTest.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = BlogController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogService blogService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private JwtFilter jwtFilter;


    @Test
    void testGetBlogById() throws Exception {
        mockMvc.perform(get("/blog/byID").param("id", "1"));
    }

    @Test
    void testGetBlogByName() throws Exception {
        mockMvc.perform(get("/blog/all"));
    }
    @Test
    void testGetBlogByAuthor() throws Exception {
        mockMvc.perform(get("/blog/byAuthor"));
    }
}
