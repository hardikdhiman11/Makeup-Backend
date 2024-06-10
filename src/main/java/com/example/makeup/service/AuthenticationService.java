package com.example.makeup.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Authentication getAuthentication(String username);
}
