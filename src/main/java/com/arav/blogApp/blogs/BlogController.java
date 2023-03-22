package com.arav.blogApp.blogs;

import com.arav.blogApp.blogs.blogDtos.BlogResponseDto;
import com.arav.blogApp.blogs.blogDtos.CreateBlogRequestDto;
import com.arav.blogApp.users.userDtos.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("")
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }
    @PostMapping("/blogs")
    public ResponseEntity<BlogResponseDto> createBlog(@RequestBody CreateBlogRequestDto blogRequestDto,
                                                      @AuthenticationPrincipal UserResponseDto user){
        var createdBlog = blogService.createBlog(blogRequestDto,user);
        return ResponseEntity.created(URI.create("/blogs/"+createdBlog.getId())).body(createdBlog);
    }
}
