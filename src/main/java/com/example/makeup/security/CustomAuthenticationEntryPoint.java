package com.example.makeup.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("Inside Custom authentication Entry Point");
        log.info("Debugging request :");
        log.info("Request Method: {}",request.getMethod());
        log.info("Authentication type {}",request.getAuthType());
        log.info("Authentication exception message {}",authException.getMessage());
        log.info("Authentication exception : \n getCause: {} \n getStackTrace: {} ",
                authException.getCause(),
                Arrays.stream(authException.getStackTrace()).collect(Collectors.toList()));
        exceptionResolver.resolveException(request,response,null,authException);
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getOutputStream().println("{ \"error\": \"" + authException.getMessage() + "\" }");
    }
}
