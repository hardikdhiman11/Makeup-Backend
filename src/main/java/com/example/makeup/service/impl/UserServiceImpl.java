package com.example.makeup.service.impl;

import com.example.makeup.dto.UserDto;
import com.example.makeup.dto.mapper.UserMapper;
import com.example.makeup.entity.User;
import com.example.makeup.exception.UserNotFoundException;
import com.example.makeup.repo.UserRepository;
import com.example.makeup.utils.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final PasswordEncoder bCryptPasswordEncoder;

    public boolean addUser(UserDto userDto){
        try {
            User user = userMapper.userDtoToUser(userDto);
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.info("Some exception occured in saving user.");
           return false;
        }
    }

    public UserDto getUserByEmail(String email){
        var user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(MessageResponse.USER_NOT_PRESENT));
        var userDto =  userMapper.userToUserDto(user);
        log.info("Email from userDto: {}",userDto.getEmail());
        return userDto;
    }
    public UserDto getUserById(long id){
        var user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        return userMapper.userToUserDto(user);
    }
}
