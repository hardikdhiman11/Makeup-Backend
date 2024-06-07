package com.example.makeup.jwt;

import com.example.makeup.service.JpaUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private final JpaUserDetailsService userDetailsService;
    @Autowired
    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. Create an authentication object which has not yet been created.
        // 2.Delegate the authentication object to the manager.
        // 3.Get back the authentication from the manager.
        // 4.If the object is authenticated then send the request to the next filter in the chain.
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);

        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            return;
        }
        if (jwt!=null && jwtUtils.validateJwt(jwt)){
            String username = jwtUtils.getUserNameFromJwt(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                            userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        }
        filterChain.doFilter(request,response);
    }
}
