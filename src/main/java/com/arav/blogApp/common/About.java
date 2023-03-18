package com.arav.blogApp.common;

import com.arav.blogApp.users.userDtos.UserResponseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/about")
public class About {
    @GetMapping("")
    public String about()
    {
        return "This is a blog app";
    }

    @GetMapping("/private")
    public String privateAbout(@AuthenticationPrincipal UserResponseDto user)
    {
        var username = user.getUsername();
        return "This is a private about page and you are logged in as "+username+"!";
    }
}
