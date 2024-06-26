package com.example.makeup.config;

import com.example.makeup.jwt.JwtAuthenticationFilter;
import com.example.makeup.security.CustomAccessDeniedHandler;
import com.example.makeup.security.CustomAuthenticationEntryPoint;
import com.example.makeup.service.JpaUserDetailsService;
import com.example.makeup.utils.WHITELIST_UTILS;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    @Autowired
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private final JpaUserDetailsService userDetailsService;
    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new DaoAuthenticationProvider();
    }

    @Bean
    public SecurityFilterChain securityFilterChainSwagger(HttpSecurity httpSecurity) throws Exception {

         httpSecurity
                 .csrf(c -> c.disable())
                 .authorizeHttpRequests(request -> {
                             request
                                     .requestMatchers(HttpMethod.POST,WHITELIST_UTILS.SWAGGER).permitAll()
                                     .requestMatchers(HttpMethod.GET,WHITELIST_UTILS.SWAGGER).permitAll()
                                     .requestMatchers(HttpMethod.PUT,WHITELIST_UTILS.SWAGGER).permitAll()
                                     .requestMatchers(HttpMethod.DELETE,WHITELIST_UTILS.SWAGGER).permitAll()
                                     .requestMatchers(HttpMethod.PATCH,WHITELIST_UTILS.SWAGGER).permitAll()
                                     .requestMatchers(HttpMethod.POST,WHITELIST_UTILS.POST_REQUESTS).permitAll()
                                     .anyRequest().authenticated();
                         }
                 )
                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                 .authenticationProvider(authenticationProvider())
                 .sessionManagement(sessionManagement->
                         sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .exceptionHandling(e->{
                     e.authenticationEntryPoint(customAuthenticationEntryPoint);
                     e.accessDeniedHandler(customAccessDeniedHandler);
                 });
         return httpSecurity.build();
    }

}
