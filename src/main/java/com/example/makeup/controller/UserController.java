package com.example.makeup.controller;

import com.example.makeup.dto.UserDto;
import com.example.makeup.repo.UserRepository;
import com.example.makeup.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserServiceImpl userService;
    @GetMapping("/hello")
    public String hello(){
        return "Hello Spring Boot";
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserByEmail(@RequestParam("email") String email){
        var userDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDto);
    }
//    @GetMapping("/user")
//    public ResponseEntity<?> getUserById(@RequestParam("id") long id){
//        return new ResponseEntity<>(userService.getUserById(id), HttpStatusCode.valueOf(200));
//    }
    @PostMapping("/user")
    public String addUser(@RequestBody UserDto userDto){
        log.info("Inside user post request controller");
        userService.addUser(userDto);
        return "User saved successfully";
    }


}
