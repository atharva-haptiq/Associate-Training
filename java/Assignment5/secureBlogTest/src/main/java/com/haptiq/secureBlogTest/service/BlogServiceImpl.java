package com.haptiq.secureBlogTest.service;

import com.haptiq.secureBlogTest.dto.BlogDTO;
import com.haptiq.secureBlogTest.entity.Blog;
import com.haptiq.secureBlogTest.entity.User;
import com.haptiq.secureBlogTest.globalResponse.ApiResponse;
import com.haptiq.secureBlogTest.repository.BlogRepository;
import com.haptiq.secureBlogTest.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public ResponseEntity<ApiResponse<?>> createBlog(com.haptiq.secureBlogTest.dto.@Valid BlogDTO blogDTO, String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "User not found", null));
        }

        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setDescription(blogDTO.getDescription());
        blog.setContent(blogDTO.getContent());
        blog.setAuthor(user);

        Blog savedBlog = blogRepository.save(blog);
        if (savedBlog == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ApiResponse<>(false, HttpStatus.NOT_ACCEPTABLE, "Failed to save blog", null));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, HttpStatus.CREATED, "Blog added successfully", null));
    }

    @Override
    public ResponseEntity<ApiResponse<?>> updateBlog(com.haptiq.secureBlogTest.dto.@Valid BlogDTO blogDTO, Long id, String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "User not found", null));
        }

        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "Blog not found", null));
        }

        blog.setTitle(blogDTO.getTitle());
        blog.setDescription(blogDTO.getDescription());
        blog.setContent(blogDTO.getContent());
        blog.setAuthor(user);

        Blog savedBlog = blogRepository.save(blog);
        if (savedBlog == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ApiResponse<>(false, HttpStatus.NOT_ACCEPTABLE, "Failed to update blog", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK, "Blog updated successfully", null));
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "Blog not found", null));
        }

        blogRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK, "Blog deleted successfully", null));
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getBlogById(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "Blog not found", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK, "Blog fetched successfully", blog));
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getAllBlogs(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Blog> blogPage = blogRepository.findAll(pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("content", blogPage.getContent());
        response.put("totalElements", blogPage.getTotalElements());
        response.put("hasNext", blogPage.hasNext());

        return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK, "All blogs fetched successfully", response));
    }

    @Override
    public ResponseEntity<ApiResponse<?>> getBlogByAuthor(Long authorID, int pageNumber, int pageSize) {
        User author = userRepository.findById(authorID).orElse(null);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, HttpStatus.NOT_FOUND, "Author not found", null));
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Blog> blogPage = blogRepository.findByAuthor(author, pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("content", blogPage.getContent());
        response.put("totalElements", blogPage.getTotalElements());
        response.put("hasNext", blogPage.hasNext());

        return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK, "Blogs by author fetched successfully", response));
    }
}
