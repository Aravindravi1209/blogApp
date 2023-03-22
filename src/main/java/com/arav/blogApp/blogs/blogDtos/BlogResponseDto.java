package com.arav.blogApp.blogs.blogDtos;

import com.arav.blogApp.comments.CommentEntity;
import com.arav.blogApp.users.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;
@Data
public class BlogResponseDto {
    private Long id;
    private String title;
    private String slug;
    private String subtitle;
    private String body;
    private String author;
    private Set<UserEntity> likers;
    private Set<CommentEntity> comments;

    private List<String> tags;
}
