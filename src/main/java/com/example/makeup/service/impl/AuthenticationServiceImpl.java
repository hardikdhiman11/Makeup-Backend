package com.example.makeup.service.impl;

import com.example.makeup.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication getAuthentication(String email){
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        Authentication authentication=
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                        userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

}
