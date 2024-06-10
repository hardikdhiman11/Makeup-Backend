package com.example.makeup.service;

import com.example.makeup.exception.UserNotFoundException;
import com.example.makeup.repo.UserRepository;
import com.example.makeup.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("customUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = userRepo.findByEmail(username);
        return optionalUser.map(user->new SecurityUser(user))
                .orElseThrow(()-> new UserNotFoundException("User not found by email"));
    }
}
