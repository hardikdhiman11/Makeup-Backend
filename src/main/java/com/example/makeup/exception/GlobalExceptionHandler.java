package com.example.makeup.exception;

import com.example.makeup.utils.MessageResponse;
import jakarta.persistence.NonUniqueResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> parentExceptionHandling(Exception e){
        log.info("Inside GlobalExceptionHandler: ParentExceptionHandler method");
        Map<String,String> map = new HashMap<>();
        map.put("message",MessageResponse.INTERNAL_SERVER_ERROR );
        return new  ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PasswordNotMatchedException.class)
    public ResponseEntity<?> passwordNotMatchedException(PasswordNotMatchedException e){
        log.info("Inside GlobalExceptionHandler: ParentExceptionHandler method");
        Map<String,String> map = new HashMap<>();
        map.put("message",MessageResponse.UNMATCHED_PASSWORD );
        return new  ResponseEntity<>(map, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({NonUniqueResultException.class, IncorrectResultSizeDataAccessException.class})
    public ResponseEntity<?> NonUniqueResultException(Exception e){
        log.info("Inside GlobalExceptionHandler: NonUniqueResultException method");
        Map<String,String> map = new HashMap<>();
        map.put("message", MessageResponse.NO_UNIQUE_RESULT);
        return new  ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundException(UserNotFoundException e){
        log.info("Inside Global exception handler: UserNotFoundException method");
        Map<String,String> map = new HashMap<>();
        map.put("message","User not found");
        return new  ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(AccessDeniedException e){
        log.info("Inside Global exception handler AccessDeniedException method");
        Map<String,String> map = new HashMap<>();
        map.put("message","Access Denied to user");
        return new  ResponseEntity<>(map, HttpStatusCode.valueOf(403));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationException(AuthenticationException e){
        log.info("Inside Global exception handler: Authentication Exception method");
        Map<String,String> map = new HashMap<>();
        map.put("message","User is not authenticated");
        return new  ResponseEntity<>(map,HttpStatusCode.valueOf(401));
    }
}
