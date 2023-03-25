package com.arav.blogApp.comments;

import com.arav.blogApp.blogs.BlogRepository;
import com.arav.blogApp.comments.commentDtos.CommentResponseDto;
import com.arav.blogApp.comments.commentDtos.CreateCommentRequestDto;
import com.arav.blogApp.comments.commentDtos.UpdateCommentRequestDto;
import com.arav.blogApp.exceptions.BadRequestException;
import com.arav.blogApp.users.UserRepository;
import com.arav.blogApp.users.userDtos.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CommentService(CommentRepository commentRepository, BlogRepository blogRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public CommentResponseDto createComment(CreateCommentRequestDto commentRequestDto, String slug, UserResponseDto user) throws BadRequestException {
        var blog = blogRepository.findBySlug(slug);
        if (blog==null) {
            throw new BadRequestException("Blog not found");
        }
        var comment = new CommentEntity();
        comment.setTitle(commentRequestDto.getTitle());
        comment.setBody(commentRequestDto.getBody());
        comment.setBlog(blog);
        comment.setAuthor(userRepository.findByUsername(user.getUsername()));
        commentRepository.save(comment);
        var response = modelMapper.map(comment, CommentResponseDto.class);
        response.setAuthor(user.getUsername());
        response.setBlogName(blog.getTitle());
        return response;
    }

    public List<CommentResponseDto> getComments(String slug) throws BadRequestException {
        var blog = blogRepository.findBySlug(slug);
        if (blog==null) {
            throw new BadRequestException("Blog not found");
        }
        var comments = commentRepository.findAllByBlog(blog);
        List<CommentResponseDto> res = new ArrayList<>();
        for (var comment : comments) {
            var response = modelMapper.map(comment, CommentResponseDto.class);
            response.setAuthor(comment.getAuthor().getUsername());
            response.setBlogName(blog.getTitle());
            res.add(response);
        }
        return res;
    }

    public CommentResponseDto updateComment(UpdateCommentRequestDto commentRequestDto, String slug, Long id, UserResponseDto user) throws BadRequestException {
        var blog = blogRepository.findBySlug(slug);
        if (blog==null) {
            throw new BadRequestException("Blog not found");
        }
        var comment = commentRepository.findById(id).get();
        if(!comment.getAuthor().getUsername().equals(user.getUsername())){
            throw new BadRequestException("You are not the author of this comment");
        }
        if(commentRequestDto.getTitle()!=null){
            comment.setTitle(commentRequestDto.getTitle());
        }
        if(commentRequestDto.getBody()!=null){
            comment.setBody(commentRequestDto.getBody());
        }
        commentRepository.save(comment);
        var response = modelMapper.map(comment, CommentResponseDto.class);
        response.setAuthor(user.getUsername());
        response.setBlogName(blog.getTitle());
        return response;
    }
}
