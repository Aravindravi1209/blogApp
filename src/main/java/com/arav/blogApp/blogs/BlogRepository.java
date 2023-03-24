package com.arav.blogApp.blogs;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    BlogEntity findByAuthorUsername(String authorName);

    List<BlogEntity> findAllByAuthorUsername(String authorName, Pageable pageable);

    List<BlogEntity> findAllByAuthorUsername(String authorName);

    BlogEntity findBySlug(String slug);

    List<BlogEntity> findAllByTags(String tag, Pageable pageable);

    List<BlogEntity> findAllByTags(String tag);
}
