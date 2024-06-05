package com.example.makeup.controller;

import com.example.makeup.exception.UserNotFoundException;
import com.example.makeup.repo.UserRepository;
import com.example.makeup.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Boot";
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUser(@RequestParam("email") String email){
        log.info("Finding user by email string");
        userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found by this id"));
        return ResponseEntity.ok("User found");
    }

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestParam("email") String email,
                                          @RequestParam("password")String password){
        userService.addUser(email,password);
        return ResponseEntity.ok("User saved successfully");
    }


}
