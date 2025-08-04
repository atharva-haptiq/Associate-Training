package com.haptiq.blogApp.repository;

import com.haptiq.blogApp.entity.Blog;
import com.haptiq.blogApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findById(Long id);
    List<Blog> findByAuthor(User author);

}
