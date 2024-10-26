package com.deep.library.services.impl;

import com.deep.library.domains.dto.UserRequest;
import com.deep.library.domains.dto.UserResponse;
import com.deep.library.domains.entities.UserEntity;
import com.deep.library.domains.mappers.UserMapper;
import com.deep.library.exceptions.BookNotFoundException;
import com.deep.library.exceptions.UserExistsException;
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

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.username()).isPresent()) {
            throw UserExistsException.forUsername(userRequest.username());
        }
        UserEntity user = new UserEntity();
        user.setUsername(userRequest.username());
        userRepository.save(user);

        return userMapper.entityToResponse(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw UserNotFoundException.forId(userId);
        }
        userRepository.deleteById(userId);
    }
}