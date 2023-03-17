package com.arav.blogApp.users;

import com.arav.blogApp.security.jwt.JwtService;
import com.arav.blogApp.users.dtos.CreateUserRequestDto;
import com.arav.blogApp.users.dtos.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public UserService(UserRepository userRepository, ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponseDto createUser(CreateUserRequestDto requestDto) {
        UserEntity userEntity = modelMapper.map(requestDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity savedUser = userRepository.save(userEntity);
        var token = jwtService.createJwt(savedUser.getUsername());
        var response = modelMapper.map(savedUser, UserResponseDto.class);
        response.setToken(token);
        return response;
    }

    public UserResponseDto verifyUser(CreateUserRequestDto requestDto) {
        UserEntity userEntity = userRepository.findByUsername(requestDto.getUsername());
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
        if(!passwordEncoder.matches(requestDto.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        var response = modelMapper.map(userEntity, UserResponseDto.class);
        response.setToken(jwtService.createJwt(userEntity.getUsername()));
        return response;
    }

    public UserResponseDto findUserbyUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return modelMapper.map(userEntity, UserResponseDto.class);
    }
}
