package com.example.makeup.service;

import com.example.makeup.entity.User;
import com.example.makeup.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl {

    @Autowired
    private final UserRepository userRepository;

    public boolean addUser(String email,String password){
        try {
            User user = User.builder().email(email).password(password).build();
            userRepository.save(user);
            return true;
        }catch (Exception e){
           return false;
        }
    }
}
