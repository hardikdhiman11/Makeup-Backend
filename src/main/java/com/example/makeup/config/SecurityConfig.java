package com.example.makeup.config;

import com.example.makeup.security.CustomAccessDeniedHandler;
import com.example.makeup.security.CustomAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

     private static final String[] WHITELIST = {
             "/api/**",
             "/api/user/**",

             "/swagger-ui/**",
             "/v3/api-docs/**",
             "/swagger-resources/**",
             "/swagger-resources",
             "/swagger-ui.html/**",


    };

    @Autowired
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

         httpSecurity
                 .csrf(c->c.disable())
                 .authorizeHttpRequests(request-> request
                         .requestMatchers(WHITELIST)
                         .permitAll()
                         .anyRequest()
                         .authenticated())
                 .sessionManagement(sessionManagement->
                         sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .exceptionHandling(e->{
                     e.authenticationEntryPoint(customAuthenticationEntryPoint);
                     e.accessDeniedHandler(customAccessDeniedHandler);
                 });
         return httpSecurity.build();
    }
}
