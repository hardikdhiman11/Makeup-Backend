package com.example.makeup.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView UserNotFoundException(UserNotFoundException e){
        log.info("Inside Global exception handler UserNotFoundException method");
        ModelAndView mav = new ModelAndView();
        mav.addObject("message",e.getMessage());
        return mav;
    }
}
