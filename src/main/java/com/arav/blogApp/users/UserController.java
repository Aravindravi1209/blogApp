package com.arav.blogApp.users;

import com.arav.blogApp.users.dtos.CreateUserRequestDto;
import com.arav.blogApp.users.dtos.UserResponseDto;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserRequestDto requestDto)
    {
        var createdUser = userService.createUser(requestDto);
        return ResponseEntity.created(URI.create("/users/"+createdUser.getId())).body(createdUser);
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody CreateUserRequestDto request)
    {
        var verifiedUser = userService.verifyUser(request);
        return ResponseEntity.ok(verifiedUser);
    }
}
