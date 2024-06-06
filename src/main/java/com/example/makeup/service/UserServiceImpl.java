package com.example.makeup.service;

import com.example.makeup.dto.UserDto;
import com.example.makeup.dto.mapper.UserMapper;
import com.example.makeup.entity.User;
import com.example.makeup.exception.UserNotFoundException;
import com.example.makeup.repo.UserRepository;
import com.example.makeup.utils.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl {

    @Autowired
    private final UserRepository userRepository;

    public boolean addUser(UserDto userDto){
        try {
            User user = User.builder()
                    .email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .name(userDto.getName())
                    .mobileNumber(userDto.getMobileNumber())
                    .build();
            log.info("User built");
            userRepository.save(user);
            log.info("User saved in repository");
            return true;
        }catch (Exception e){
            log.info("Some exception occured in saving user.");
           return false;
        }
    }

    public UserDto getUserByEmail(String email){
        var user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(MessageResponse.USER_NOT_PRESENT));
        return UserMapper.INSTANCE.userToUserDto(user);
    }
}
