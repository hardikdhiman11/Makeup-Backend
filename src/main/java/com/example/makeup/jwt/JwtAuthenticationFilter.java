package com.example.makeup.jwt;

import com.example.makeup.service.JpaUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JpaUserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    public JwtAuthenticationFilter(JpaUserDetailsService userDetailsService,JwtUtils jwtUtils){
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. Create an authentication object which has not yet been created.
        // 2.Delegate the authentication object to the manager.
        // 3.Get back the authentication from the manager.
        // 4.If the object is authenticated then send the request to the next filter in the chain.
        log.info("Inside jwt Authentication filter");
        log.info("Getting authHeader from request.");
        String authHeader = request.getHeader("Authorization");
        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            log.info("Returning because auth header is null or does not start with bearer.");
            return;
        }

        String jwt = authHeader.split(" ")[1].trim();


        if (jwt!=null && jwtUtils.validateJwt(jwt)){
            String username = jwtUtils.getUserNameFromJwt(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Authentication authentication=
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                            userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }
}
