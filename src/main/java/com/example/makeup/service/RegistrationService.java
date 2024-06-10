package com.example.makeup.service;

import com.example.makeup.dto.LoginDto;
import com.example.makeup.dto.UserDto;
import com.example.makeup.entity.User;
import com.example.makeup.exception.PasswordNotMatchedException;

public interface RegistrationService {
    String registerUser(UserDto userDto);
    String loginUser(LoginDto loginDto) throws PasswordNotMatchedException;
    boolean deleteUser(long id);

}
