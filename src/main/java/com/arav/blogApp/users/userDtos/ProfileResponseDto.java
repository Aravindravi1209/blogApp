package com.arav.blogApp.users.userDtos;

import com.arav.blogApp.blogs.BlogEntity;
import com.arav.blogApp.users.UserEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
public class ProfileResponseDto {
    private String username;
    private String email;
    private String bio;
    private String image;
    private Set<BlogEntity> authoredBlogs;
    private Set<BlogEntity> likedBlogs;
    private Set<UserEntity> followers;
    private Set<UserEntity> following;
}
