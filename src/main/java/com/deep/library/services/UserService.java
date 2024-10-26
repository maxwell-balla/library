package com.deep.library.services;

import com.deep.library.domains.dto.UserRequest;
import com.deep.library.domains.dto.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    void deleteUser(Long userId);
}
