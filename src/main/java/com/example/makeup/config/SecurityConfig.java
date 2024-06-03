package com.example.makeup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {

    private static final String[] WHITELIST = {
            "api/*"
    };
    @Bean
    public HttpSecurity securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    return     httpSecurity.
                authorizeHttpRequests(request->
                        request.requestMatchers(WHITELIST).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form->form.loginPage("/login").permitAll());
    }
}
