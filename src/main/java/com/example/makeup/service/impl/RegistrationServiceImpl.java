package com.example.makeup.service.impl;

import com.example.makeup.dto.LoginDto;
import com.example.makeup.dto.UserDto;
import com.example.makeup.dto.mapper.UserMapper;
import com.example.makeup.entity.User;
import com.example.makeup.exception.PasswordNotMatchedException;
import com.example.makeup.exception.UserNotFoundException;
import com.example.makeup.jwt.JwtUtils;
import com.example.makeup.repo.UserRepository;
import com.example.makeup.service.AuthenticationService;
import com.example.makeup.service.RegistrationService;
import com.example.makeup.utils.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationService authenticationService;
    @Autowired
    public RegistrationServiceImpl(UserMapper userMapper,
                                   UserRepository userRepository,
                                   PasswordEncoder bCryptPasswordEncoder,
                                   JwtUtils jwtUtils,
                                   AuthenticationService authenticationService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils=jwtUtils;
        this.authenticationService = authenticationService;
    }

    @Override
    public String registerUser(UserDto userDto) {
            User mappedUser = userMapper.userDtoToUser(userDto);
            User user = userRepository.findByEmail(mappedUser.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found by this email"));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Authentication authentication = authenticationService.getAuthentication(user.getEmail());
            String jwt = jwtUtils.generateJwt(authentication);
            userRepository.save(user);
            return jwt;
    }

    @Override
    public String loginUser(LoginDto loginDto) throws PasswordNotMatchedException {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found by this email"));
        if (bCryptPasswordEncoder.matches(password,user.getPassword())){
            Authentication authentication = authenticationService.getAuthentication(user.getEmail());
            String jwt = jwtUtils.generateJwt(authentication);
            return jwt;
        }
        throw new PasswordNotMatchedException(MessageResponse.UNMATCHED_PASSWORD);
    }

    @Override
    public boolean deleteUser(long id) {
        userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(MessageResponse.USER_NOT_PRESENT));
        userRepository.deleteById(id);
        return true;
    }
}
