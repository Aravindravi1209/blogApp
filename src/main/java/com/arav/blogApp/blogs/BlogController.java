package com.arav.blogApp.blogs;

import com.arav.blogApp.blogs.blogDtos.BlogResponseDto;
import com.arav.blogApp.blogs.blogDtos.CreateBlogRequestDto;
import com.arav.blogApp.exceptions.BadRequestException;
import com.arav.blogApp.users.userDtos.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogResponseDto>> getAllBlogs(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize) throws BadRequestException {
        return ResponseEntity.ok(blogService.getAllBlogs(pageNumber,pageSize));
    }

    @GetMapping(value = "/blogs",params = "authorName")
    public ResponseEntity<List<BlogResponseDto>> getBlogByAuthorName(@RequestParam String authorName) throws BadRequestException {
        return ResponseEntity.ok(blogService.getBlogByAuthorName(authorName));
    }
}
