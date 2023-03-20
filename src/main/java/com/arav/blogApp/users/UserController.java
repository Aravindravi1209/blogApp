package com.arav.blogApp.users;

import com.arav.blogApp.exceptions.AccessDeniedException;
import com.arav.blogApp.exceptions.BadRequestException;
import com.arav.blogApp.users.userDtos.CreateUserRequestDto;
import com.arav.blogApp.users.userDtos.ProfileResponseDto;
import com.arav.blogApp.users.userDtos.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserRequestDto requestDto) throws BadRequestException {
        var createdUser = userService.createUser(requestDto);
        return ResponseEntity.created(URI.create("/users/"+createdUser.getId())).body(createdUser);
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody CreateUserRequestDto request) throws BadRequestException, AccessDeniedException {
        var verifiedUser = userService.verifyUser(request);
        return ResponseEntity.ok(verifiedUser);
    }

    @GetMapping("/profiles")
    public ResponseEntity<List<ProfileResponseDto>> getAllProfiles(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize
    ) throws BadRequestException {
        return ResponseEntity.ok(userService.getAllProfiles(pageNumber, pageSize));
    }

    @GetMapping("/profiles/{username}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable String username) throws BadRequestException {
        return ResponseEntity.ok(userService.getProfile(username));
    }
}
