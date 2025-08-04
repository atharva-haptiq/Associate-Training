package com.haptiq.blogApp.service;

import com.haptiq.blogApp.dto.BlogDTO;
import com.haptiq.blogApp.dto.UserDTO;
import com.haptiq.blogApp.entity.Blog;
import com.haptiq.blogApp.entity.User;
import com.haptiq.blogApp.repository.BlogRepository;
import com.haptiq.blogApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public ResponseEntity<?> createBlog(BlogDTO blogDTO) {
        User user = userRepository.findByFirstName(blogDTO.getAuthorFirstName()).orElse(null);
        if (user ==null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setDescription(blogDTO.getDescription());
        blog.setContent(blogDTO.getContent());
        blog.setAuthor(user);
        Blog savedBlog = blogRepository.save(blog);
        if (savedBlog == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        else return ResponseEntity.ok("Blog added succesfully");
    }

    @Override
    public ResponseEntity<?> updateBlog(BlogDTO blogDTO, Long id) {
        User user = userRepository.findByFirstName(blogDTO.getAuthorFirstName()).orElse(null);
        if (user ==null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog ==null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        blog.setTitle(blogDTO.getTitle());
        blog.setDescription(blogDTO.getDescription());
        blog.setContent(blogDTO.getContent());
        blog.setAuthor(user);
        Blog savedBlog = blogRepository.save(blog);
        if (savedBlog == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        else return ResponseEntity.ok("Blog added succesfully");
    }

    @Override
    public ResponseEntity<?> deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        blogRepository.deleteById(id);
        return ResponseEntity.ok("Blog deleted Successfully");
    }

    @Override
    public ResponseEntity<?> getBlogById(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(blog);
    }

    @Override
    public ResponseEntity<?> getAllBlogs() {
        List<Blog> blogList = blogRepository.findAll();
        if (blogList == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(blogList);
    }

    @Override
    public ResponseEntity<?> getBlogByAuthor(Long authorID) {
        User author = userRepository.findById(authorID).orElse(null);
        if (author ==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<Blog> blogList = blogRepository.findByAuthor(author);
        if (blogList.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(blogList);
    }
}
