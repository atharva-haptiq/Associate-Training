package com.haptiq.blogApp.service;

import com.haptiq.blogApp.dto.BlogDTO;
import com.haptiq.blogApp.entity.Blog;
import org.springframework.http.ResponseEntity;

public interface BlogService {
    ResponseEntity<?> createBlog(BlogDTO blogDTO);
    ResponseEntity<?> updateBlog(BlogDTO blogDTO, Long id);
    ResponseEntity<?> deleteBlog(Long id);
    ResponseEntity<?> getBlogById(Long id);
    ResponseEntity<?> getAllBlogs(int pageNumber, int pageSize);
    ResponseEntity<?> getBlogByAuthor(Long authorID, int pageNumber, int pageSize);
}
