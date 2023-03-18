package com.arav.blogApp.users.userDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequestDto {

    @NonNull
    private String username;
    @NonNull
    private String password;
}
