package com.arav.blogApp.blogs.blogDtos;

import com.arav.blogApp.users.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class CreateBlogRequestDto {
    @NonNull
    private String title;
    @NonNull
    private String slug;
    @NonNull
    private String subtitle;
    @NonNull
    private String body;
}
