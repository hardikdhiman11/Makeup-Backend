package com.example.makeup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private static final String[] WHITELIST = {
            "/api/*",
            "/swagger-ui.html/*",
            "/v3/api-docs/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources",
            "/swagger-ui/**",
            "/login"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

         httpSecurity.
                authorizeHttpRequests(request->
                        request.requestMatchers(WHITELIST).permitAll()
                                .anyRequest().authenticated()
                )
                .logout(logout->logout.logoutUrl("/").permitAll());
         return httpSecurity.build();
    }
}
