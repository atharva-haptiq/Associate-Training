package com.haptiq.blogApp.repository;

import com.haptiq.blogApp.entity.Blog;
import com.haptiq.blogApp.entity.Comment;
import com.haptiq.blogApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBlog(Blog blog);
    List<Comment> findByUser(User user);

}
