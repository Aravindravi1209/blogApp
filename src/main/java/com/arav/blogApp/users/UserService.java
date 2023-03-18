package com.arav.blogApp.users;

import com.arav.blogApp.common.EmailValidator;
import com.arav.blogApp.exceptions.AccessDeniedException;
import com.arav.blogApp.exceptions.BadRequestException;
import com.arav.blogApp.security.jwt.JwtService;
import com.arav.blogApp.users.userDtos.CreateUserRequestDto;
import com.arav.blogApp.users.userDtos.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private EmailValidator emailValidator;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtService jwtService, EmailValidator emailValidator) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailValidator = emailValidator;
    }

    public UserResponseDto createUser(CreateUserRequestDto requestDto) throws BadRequestException {
        if(userRepository.findByUsername(requestDto.getUsername()) != null) {
            throw new BadRequestException("User with username: "+requestDto.getUsername()+" already exists.");
        }
        if(userRepository.findByEmail(requestDto.getEmail()) != null) {
            throw new BadRequestException("User with email: "+requestDto.getEmail()+" already exists.");
        }
        if(requestDto.getPassword().length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long.");
        }
        if(!emailValidator.isValidEmail(requestDto.getEmail())) {
            throw new BadRequestException("Email provided is not valid.");
        }
        UserEntity userEntity = modelMapper.map(requestDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity savedUser = userRepository.save(userEntity);
        var token = jwtService.createJwt(savedUser.getUsername());
        var response = modelMapper.map(savedUser, UserResponseDto.class);
        response.setToken(token);
        return response;
    }

    public UserResponseDto verifyUser(CreateUserRequestDto requestDto) throws BadRequestException, AccessDeniedException {
        UserEntity userEntity = userRepository.findByUsername(requestDto.getUsername());
        if (userEntity == null) {
            throw new BadRequestException("User with username: "+requestDto.getUsername()+" does not exist.");
        }
        if(!passwordEncoder.matches(requestDto.getPassword(), userEntity.getPassword())) {
            throw new AccessDeniedException("Incorrect Password Provided!");
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
