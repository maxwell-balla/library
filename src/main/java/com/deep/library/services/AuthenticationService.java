package com.deep.library.services;

import com.deep.library.domains.dto.AuthRequest;
import com.deep.library.domains.dto.AuthResponse;

public interface AuthenticationService {
    AuthResponse register(AuthRequest request);

    AuthResponse login(AuthRequest request);

    AuthResponse becomeAdmin(AuthRequest request);
}
