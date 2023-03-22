package com.arav.blogApp.users;

import com.arav.blogApp.common.EmailValidator;
import com.arav.blogApp.exceptions.AccessDeniedException;
import com.arav.blogApp.exceptions.BadRequestException;
import com.arav.blogApp.security.jwt.JwtService;
import com.arav.blogApp.users.userDtos.CreateUserRequestDto;
import com.arav.blogApp.users.userDtos.ProfileResponseDto;
import com.arav.blogApp.users.userDtos.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    private void verifyDetails(CreateUserRequestDto requestDto) throws BadRequestException {
        if(userRepository.findByUsername(requestDto.getUsername()) != null) {
            throw new BadRequestException("User with username: "+requestDto.getUsername()+" already exists.");
        }
        if(userRepository.findByEmail(requestDto.getEmail()) != null) {
            throw new BadRequestException("User with email: "+requestDto.getEmail()+" already exists.");
        }
        if(requestDto.getPassword().length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long.");
        }
        if(requestDto.getEmail()==null || requestDto.getEmail().isEmpty()) {
            throw new BadRequestException("Email cannot be empty.");
        }
        if(!emailValidator.isValidEmail(requestDto.getEmail())) {
            throw new BadRequestException("Email provided is not valid.");
        }
    }

    public UserResponseDto createUser(CreateUserRequestDto requestDto) throws BadRequestException {
        verifyDetails(requestDto);
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

    public List<ProfileResponseDto> getAllProfiles(Integer pageNumber, Integer pageSize) throws BadRequestException {

        Pageable p = PageRequest.of(pageNumber, pageSize);
        List<UserEntity> userEntities = userRepository.findAll(p).getContent();
        if(userEntities.isEmpty()) {
            throw new BadRequestException("No users found.");
        }
        return userEntities.stream().map(userEntity -> modelMapper.map(userEntity, ProfileResponseDto.class))
                .collect(Collectors.toList());
    }

    public ProfileResponseDto getProfile(String username) throws BadRequestException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
            throw new BadRequestException("User with username: "+username+" does not exist.");
        }
        return modelMapper.map(userEntity, ProfileResponseDto.class);
    }
}
