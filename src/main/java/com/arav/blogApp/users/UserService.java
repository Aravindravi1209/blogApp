package com.arav.blogApp.users;

import com.arav.blogApp.users.dtos.CreateUserRequestDto;
import com.arav.blogApp.users.dtos.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(CreateUserRequestDto requestDto) {
        UserEntity userEntity = modelMapper.map(requestDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity savedUser = userRepository.save(userEntity);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    public UserResponseDto verifyUser(CreateUserRequestDto requestDto) {
        UserEntity userEntity = userRepository.findByUsername(requestDto.getUsername());
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
        if(!passwordEncoder.matches(requestDto.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        return modelMapper.map(userEntity, UserResponseDto.class);
    }
}
