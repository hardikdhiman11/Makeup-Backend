package com.example.makeup.exception;

public class PasswordNotMatchedException extends Exception{
    public PasswordNotMatchedException(String message){
        super(message);
    }
}
