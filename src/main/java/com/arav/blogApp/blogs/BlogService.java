package com.arav.blogApp.blogs;

import com.arav.blogApp.blogs.blogDtos.BlogResponseDto;
import com.arav.blogApp.blogs.blogDtos.CreateBlogRequestDto;
import com.arav.blogApp.users.UserRepository;
import com.arav.blogApp.users.userDtos.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public BlogService(BlogRepository blogRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public BlogResponseDto createBlog(CreateBlogRequestDto blogRequestDto, UserResponseDto user) {
        var blogEntity = modelMapper.map(blogRequestDto, BlogEntity.class);
        blogEntity.setAuthor(userRepository.findByUsername(user.getUsername()));
        var savedBlog = blogRepository.save(blogEntity);
        var response = modelMapper.map(savedBlog, BlogResponseDto.class);
        response.setAuthor(user.getUsername());
        return response;
    }
}
