package com.arav.blogApp.comments;

import com.arav.blogApp.comments.commentDtos.CommentResponseDto;
import com.arav.blogApp.comments.commentDtos.CreateCommentRequestDto;
import com.arav.blogApp.comments.commentDtos.UpdateCommentRequestDto;
import com.arav.blogApp.exceptions.BadRequestException;
import com.arav.blogApp.users.userDtos.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/blogs")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("{slug}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CreateCommentRequestDto commentRequestDto,
                                                            @PathVariable String slug,
                                                            @AuthenticationPrincipal UserResponseDto user) throws BadRequestException {
        var createdComment = commentService.createComment(commentRequestDto,slug,user);
        return ResponseEntity.created(URI.create("/blogs/"+slug+"/comments/"+createdComment.getId())).body(createdComment);
    }

    @GetMapping("{slug}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable String slug) throws BadRequestException {
        var comments = commentService.getComments(slug);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("{slug}/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@RequestBody UpdateCommentRequestDto commentRequestDto,
                                                            @PathVariable String slug,
                                                            @PathVariable Long id,
                                                            @AuthenticationPrincipal UserResponseDto user) throws BadRequestException {
        var updatedComment = commentService.updateComment(commentRequestDto,slug,id,user);
        return ResponseEntity.ok(updatedComment);
    }
}
