package com.haptiq.blogApp.controller;

import com.haptiq.blogApp.dto.BlogDTO;
import com.haptiq.blogApp.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/add")
    public ResponseEntity<?> createBlog(@Valid @RequestBody BlogDTO blogDTO){
        return blogService.createBlog(blogDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBlog(@Valid @RequestBody BlogDTO blogDTO, @RequestParam Long id){
        return blogService.updateBlog(blogDTO,id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBlog(@RequestParam Long blogID){
        return blogService.deleteBlog(blogID);
    }

    @GetMapping("/byID")
    public ResponseEntity<?> getBlogById(@RequestParam Long id){
        return blogService.getBlogById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBlogs(){
        return blogService.getAllBlogs();
    }

    @GetMapping("/byAuthor")
    public ResponseEntity<?> getBlogByAuthor(@RequestParam Long authorID,
                                             @RequestParam (required = false, defaultValue = "0") int pageNumber,
                                             @RequestParam (required = false, defaultValue = "10") int pageSize){
        return blogService.getBlogByAuthor(authorID,pageNumber,pageSize);
    }


}
