package com.deep.library.controllers;

import com.deep.library.domains.dto.AuthResponse;
import com.deep.library.domains.dto.AuthRequest;
import com.deep.library.domains.dto.RefreshRequest;
import com.deep.library.domains.dto.RefreshResponse;
import com.deep.library.security.JwtService;
import com.deep.library.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    ResponseEntity<AuthResponse> register(
            @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/admin")
    ResponseEntity<AuthResponse> becomeAdmin(
            @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(authService.becomeAdmin(request));
    }

    @PostMapping("/refresh")
    ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest request) {
        RefreshResponse newToken = jwtService.handleRefreshToken(request);
        return ResponseEntity.ok(newToken);
    }
}