package com.example.makeup.controller;

import com.example.makeup.dto.LoginDto;
import com.example.makeup.dto.UserDto;
import com.example.makeup.exception.PasswordNotMatchedException;
import com.example.makeup.exception.UserAlreadyExistsException;
import com.example.makeup.service.RegistrationService;
import com.example.makeup.utils.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Registration/Login")
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
    public ResponseEntity<Map> signup(@RequestBody UserDto userDto) throws UserAlreadyExistsException {
        String jwt = registrationService.registerUser(userDto);
        Map<String,Object> map = new HashMap<>();
        map.put("message",MessageResponse.SUCCESSFULL);
        map.put("jwt",jwt);
        log.info("Created a json object with token and message");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
