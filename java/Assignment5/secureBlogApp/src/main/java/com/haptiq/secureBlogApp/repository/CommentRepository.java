package com.haptiq.secureBlogApp.repository;


import com.haptiq.secureBlogApp.entity.Blog;
import com.haptiq.secureBlogApp.entity.Comment;
import com.haptiq.secureBlogApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByBlog(Blog blog, Pageable pageable);
    Page<Comment> findByUser(User user, Pageable pageable);

}
