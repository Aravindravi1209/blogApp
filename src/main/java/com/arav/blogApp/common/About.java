package com.arav.blogApp.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class About {
    @GetMapping("/about")
    public String about()
    {
        return "This is a blog app";
    }

    @GetMapping("/private/about")
    public String privateAbout()
    {
        return "This is a private blog app";
    }
}
