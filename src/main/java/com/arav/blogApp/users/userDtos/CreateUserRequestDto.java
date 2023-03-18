package com.arav.blogApp.users.userDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {

    @NonNull
    private String username;
    @NonNull
    private String password;

    @NonNull
    private String email;
}
