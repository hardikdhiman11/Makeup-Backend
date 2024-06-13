package com.example.makeup.service.impl;

import com.example.makeup.dto.LoginDto;
import com.example.makeup.dto.UserDto;
import com.example.makeup.dto.mapper.UserMapper;
import com.example.makeup.entity.Authority;
import com.example.makeup.entity.User;
import com.example.makeup.exception.PasswordNotMatchedException;
import com.example.makeup.exception.UserAlreadyExistsException;
import com.example.makeup.exception.UserNotFoundException;
import com.example.makeup.jwt.JwtUtils;
import com.example.makeup.repo.AuthorityRepository;
import com.example.makeup.repo.UserRepository;
import com.example.makeup.security.SecurityUser;
import com.example.makeup.service.AuthenticationService;
import com.example.makeup.service.RegistrationService;
import com.example.makeup.utils.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationService authenticationService;
    private final AuthorityRepository authorityRepository;
    @Autowired
    public RegistrationServiceImpl(UserMapper userMapper,
                                   UserRepository userRepository,
                                   PasswordEncoder bCryptPasswordEncoder,
                                   JwtUtils jwtUtils,
                                   AuthenticationService authenticationService,
                                   AuthorityRepository authorityRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils=jwtUtils;
        this.authenticationService = authenticationService;
        this.authorityRepository = authorityRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String registerUser(UserDto userDto) throws UserAlreadyExistsException {
            User user = userMapper.userDtoToUser(userDto);
            user.setAuthorities(Set.of(authorityRepository.findById(1L)
                    .orElseThrow(()-> new UserNotFoundException("Authority does not exists"))));
            if (userRepository.findById(user.getId()).isPresent()) throw new UserAlreadyExistsException();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            new SecurityUser(user);
            Authentication authentication = authenticationService.getAuthentication(user.getEmail());
            String jwt = jwtUtils.generateJwt(authentication);

            return jwt;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String loginUser(LoginDto loginDto) throws PasswordNotMatchedException {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found by this email"));
        if (bCryptPasswordEncoder.matches(password,user.getPassword())){
            Authentication authentication = authenticationService.getAuthentication(user.getEmail());
            String jwt = jwtUtils.generateJwt(authentication);
            return jwt;
        }
        throw new PasswordNotMatchedException(MessageResponse.UNMATCHED_PASSWORD);
    }

    @Override
    @Transactional
    public boolean deleteUser(long id) {
        userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(MessageResponse.USER_NOT_PRESENT));
        userRepository.deleteById(id);
        return true;
    }
}
