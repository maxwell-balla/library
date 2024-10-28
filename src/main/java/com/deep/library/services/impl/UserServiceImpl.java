package com.deep.library.services.impl;

import com.deep.library.exceptions.UserNotFoundException;
import com.deep.library.repositories.UserRepository;
import com.deep.library.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(Long userId) {
        verifiedUserExist(userId);
        userRepository.deleteById(userId);
    }

    public void verifiedUserExist(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw UserNotFoundException.forId(userId);
        }
    }
}