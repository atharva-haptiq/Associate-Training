package com.haptiq.secureBlogApp.controller;


import com.haptiq.secureBlogApp.dto.BlogDTO;
import com.haptiq.secureBlogApp.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/byID")
    public ResponseEntity<?> getBlogById(@RequestParam Long id){
        return blogService.getBlogById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBlogs(@RequestParam (required = false, defaultValue = "0") int pageNumber,
                                         @RequestParam (required = false, defaultValue = "10") int pageSize){
        return blogService.getAllBlogs(pageNumber,pageSize);
    }

    @GetMapping("/byAuthor")
    public ResponseEntity<?> getBlogByAuthor(@RequestParam Long authorID,
                                             @RequestParam (required = false, defaultValue = "0") int pageNumber,
                                             @RequestParam (required = false, defaultValue = "10") int pageSize){
        return blogService.getBlogByAuthor(authorID,pageNumber,pageSize);
    }


}
