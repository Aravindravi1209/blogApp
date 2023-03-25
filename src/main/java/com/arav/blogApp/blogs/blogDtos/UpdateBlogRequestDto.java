package com.arav.blogApp.blogs.blogDtos;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.List;

@Data
public class UpdateBlogRequestDto {
    private String title;
    private String slug;
    private String subtitle;
    private String body;
    private List<String> tags;
}
