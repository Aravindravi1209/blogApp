package com.arav.blogApp.comments.commentDtos;

import lombok.Data;

@Data
public class CommentResponseDto {
    Long id;
    String title;
    String body;
    String author;
    String blogName;
}
