package com.haptiq.secureBlogTest.repository;


import com.haptiq.secureBlogTest.entity.Blog;
import com.haptiq.secureBlogTest.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findById(Long id);
    Page<Blog> findByAuthor(User author, Pageable pageable);
    Page<Blog> findAll(Pageable pageable);

}
