package com.arav.blogApp.blogs;

import com.arav.blogApp.blogs.blogDtos.BlogResponseDto;
import com.arav.blogApp.blogs.blogDtos.CreateBlogRequestDto;
import com.arav.blogApp.exceptions.BadRequestException;
import com.arav.blogApp.users.UserRepository;
import com.arav.blogApp.users.userDtos.ProfileResponseDto;
import com.arav.blogApp.users.userDtos.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<BlogResponseDto> getAllBlogs(Integer pageNumber, Integer pageSize) throws BadRequestException {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<BlogEntity> blogEntities = blogRepository.findAll(pageable).getContent();
        if(blogEntities.isEmpty()){
            throw new BadRequestException("No blogs found.");
        }
        return blogEntities.stream().map(blogEntity -> {
            var response = modelMapper.map(blogEntity, BlogResponseDto.class);
            response.setAuthor(blogEntity.getAuthor().getUsername());
            return response;
        }).collect(Collectors.toList());

    }

    public List<BlogResponseDto> getBlogByAuthorName(String authorName) throws BadRequestException {
List<BlogEntity> blogEntities = blogRepository.findAllByAuthorUsername(authorName);
        if(blogEntities.isEmpty()){
            throw new BadRequestException("No blogs found for author: "+authorName);
        }
        return blogEntities.stream().map(blogEntity -> {
            var response = modelMapper.map(blogEntity, BlogResponseDto.class);
            response.setAuthor(blogEntity.getAuthor().getUsername());
            return response;
        }).collect(Collectors.toList());
    }
}
