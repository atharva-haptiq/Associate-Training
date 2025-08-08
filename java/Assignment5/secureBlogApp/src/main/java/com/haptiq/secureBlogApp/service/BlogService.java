package com.haptiq.secureBlogApp.service;


import com.haptiq.secureBlogApp.dto.BlogDTO;
import org.springframework.http.ResponseEntity;

public interface BlogService {
    ResponseEntity<?> createBlog(BlogDTO blogDTO, String email);
    ResponseEntity<?> updateBlog(BlogDTO blogDTO, Long id,String email);
    ResponseEntity<?> deleteBlog(Long id);
    ResponseEntity<?> getBlogById(Long id);
    ResponseEntity<?> getAllBlogs(int pageNumber, int pageSize);
    ResponseEntity<?> getBlogByAuthor(Long authorID, int pageNumber, int pageSize);
}
