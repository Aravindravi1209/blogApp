package com.arav.blogApp.blogs;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    BlogEntity findByAuthorUsername(String authorName);

    List<BlogEntity> findAllByAuthorUsername(String authorName, Pageable pageable);

    List<BlogEntity> findAllByAuthorUsername(String authorName);

    BlogEntity findBySlug(String slug);

    //SHOULD BE NORMALIZED, Currently multi valued attributes are present in the tags column
    @Query(value = "SELECT * FROM blogs where :tag=ANY(tags)", nativeQuery = true)
    List<BlogEntity> findAllByTag(String tag);
}
