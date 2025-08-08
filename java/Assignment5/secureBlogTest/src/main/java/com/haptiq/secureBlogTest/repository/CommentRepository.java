package com.haptiq.secureBlogTest.repository;


import com.haptiq.secureBlogTest.entity.Blog;
import com.haptiq.secureBlogTest.entity.Comment;
import com.haptiq.secureBlogTest.entity.User;
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
