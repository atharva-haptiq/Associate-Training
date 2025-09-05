package com.haptiq.secureBlogTest.service;


import com.haptiq.secureBlogTest.dto.BlogDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface BlogService {
    ResponseEntity<?> createBlog(com.haptiq.secureBlogTest.dto.@Valid BlogDTO blogDTO, String email);
    ResponseEntity<?> updateBlog(com.haptiq.secureBlogTest.dto.@Valid BlogDTO blogDTO, Long id, String email);
    ResponseEntity<?> deleteBlog(Long id);
    ResponseEntity<?> getBlogById(Long id);
    ResponseEntity<?> getAllBlogs(int pageNumber, int pageSize);
    ResponseEntity<?> getBlogByAuthor(Long authorID, int pageNumber, int pageSize);
}
