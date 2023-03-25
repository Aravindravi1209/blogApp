package com.arav.blogApp.blogs;

import com.arav.blogApp.blogs.blogDtos.BlogResponseDto;
import com.arav.blogApp.blogs.blogDtos.CreateBlogRequestDto;
import com.arav.blogApp.blogs.blogDtos.UpdateBlogRequestDto;
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

    public List<BlogResponseDto> getBlogByAuthorName(String authorName, Integer pageNumber, Integer pageSize) throws BadRequestException {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<BlogEntity> blogEntities = blogRepository.findAllByAuthorUsername(authorName, pageable).stream().toList();
        List<BlogEntity> totalBlogEntities = blogRepository.findAllByAuthorUsername(authorName);
        if(blogEntities.isEmpty() && totalBlogEntities.isEmpty()){
            throw new BadRequestException("No blogs found for author: "+authorName);
        }
        if(blogEntities.isEmpty()){
            throw new BadRequestException("No more blogs found for author: "+authorName);
        }
        return blogEntities.stream().map(blogEntity -> {
            var response = modelMapper.map(blogEntity, BlogResponseDto.class);
            response.setAuthor(blogEntity.getAuthor().getUsername());
            return response;
        }).collect(Collectors.toList());
    }

    public BlogResponseDto getBlogBySlug(String slug) throws BadRequestException {
        var blogEntity = blogRepository.findBySlug(slug);
        if(blogEntity == null){
            throw new BadRequestException("No blog found for slug: "+slug);
        }
        var response = modelMapper.map(blogEntity, BlogResponseDto.class);
        response.setAuthor(blogEntity.getAuthor().getUsername());
        return response;
    }

    public List<BlogResponseDto> getBlogByTag(String tag) throws BadRequestException {
        List<BlogEntity> blogEntities = blogRepository.findAllByTag(tag);
        if(blogEntities.isEmpty()){
            throw new BadRequestException("No blogs found for tag: "+tag);
        }
        return blogEntities.stream().map(blogEntity -> {
            var response = modelMapper.map(blogEntity, BlogResponseDto.class);
            response.setAuthor(blogEntity.getAuthor().getUsername());
            return response;
        }).collect(Collectors.toList());
    }

    public BlogResponseDto updateBlog(String slug, UpdateBlogRequestDto blogRequestDto, UserResponseDto user) throws BadRequestException {
        var blogEntity = blogRepository.findBySlug(slug);
        if(blogEntity == null){
            throw new BadRequestException("No blog found for slug: "+slug);
        }
        if(!blogEntity.getAuthor().getUsername().equals(user.getUsername())){
            throw new BadRequestException("You are not authorized to update this blog.");
        }
        if(blogRequestDto.getTitle() != null){
            blogEntity.setTitle(blogRequestDto.getTitle());
        }
        if(blogRequestDto.getBody() != null){
            blogEntity.setBody(blogRequestDto.getBody());
        }
        if(blogRequestDto.getTags() != null){
            blogEntity.setTags(blogRequestDto.getTags());
        }
        if(blogRequestDto.getSubtitle() != null){
            blogEntity.setSubtitle(blogRequestDto.getSubtitle());
        }
        var savedBlog = blogRepository.save(blogEntity);
        var response = modelMapper.map(savedBlog, BlogResponseDto.class);
        response.setAuthor(user.getUsername());
        return response;
    }
}
