package com.arav.blogApp.users.dtos;

import lombok.Data;
import lombok.Getter;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String token;
}
