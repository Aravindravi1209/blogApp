package com.arav.blogApp.comments.commentDtos;

import lombok.Data;

@Data
public class UpdateCommentRequestDto {
    private String title;
    private String body;
}
