package com.example.makeup.controller;

import com.example.makeup.dto.LoginDto;
import com.example.makeup.dto.UserDto;
import com.example.makeup.exception.PasswordNotMatchedException;
import com.example.makeup.exception.UserAlreadyExistsException;
import com.example.makeup.service.RegistrationService;
import com.example.makeup.utils.MessageResponse;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/login")
    public ResponseEntity<JsonObject> login(@RequestBody LoginDto loginDto) throws PasswordNotMatchedException {
        String jwt = registrationService.loginUser(loginDto);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("jwtToken",jwt);
        jsonObject.addProperty("message", MessageResponse.SUCCESSFULL);
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<JsonObject> signup(@RequestBody UserDto userDto) throws UserAlreadyExistsException {
        String jwt = registrationService.registerUser(userDto);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("jwtToken",jwt);
        jsonObject.addProperty("message", MessageResponse.SUCCESSFULL);
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
}
