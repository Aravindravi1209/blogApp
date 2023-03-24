package com.arav.blogApp.blogs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    BlogEntity findByAuthorUsername(String authorName);

    List<BlogEntity> findAllByAuthorUsername(String authorName);

}
