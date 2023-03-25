package com.arav.blogApp.comments.commentDtos;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class CreateCommentRequestDto {
    @NonNull
    private String title;
    @NonNull
    private String body;
}
