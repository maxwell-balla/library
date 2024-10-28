package com.deep.library.services.impl;

import com.deep.library.domains.Role;
import com.deep.library.domains.dto.AuthRequest;
import com.deep.library.domains.dto.AuthResponse;
import com.deep.library.domains.entities.UserEntity;
import com.deep.library.exceptions.UserExistsException;
import com.deep.library.exceptions.UserNotFoundException;
import com.deep.library.repositories.UserRepository;
import com.deep.library.security.JwtService;
import com.deep.library.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(AuthRequest request) {
        log.debug("register request:{}", request);
        if (userRepository.findByUsername(request.email()).isPresent()) {
            throw new UserExistsException("This email already exists");
        }
        UserEntity user = UserEntity.builder()
                .username(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        log.debug("login request:{}", request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        UserEntity user = userRepository.findByUsername(request.email())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        var token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponse becomeAdmin(AuthRequest request) {
        log.debug("become Admin request:{}", request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        UserEntity user = userRepository.findByUsername(request.email())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
