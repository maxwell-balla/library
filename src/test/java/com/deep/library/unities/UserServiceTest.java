package com.deep.library.unities;

import com.deep.library.domains.dto.UserResponse;
import com.deep.library.domains.entities.UserEntity;
import com.deep.library.domains.mappers.UserMapper;
import com.deep.library.exceptions.UserExistsException;
import com.deep.library.repositories.UserRepository;
import com.deep.library.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static com.deep.library.Constants.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldCreateUser_whenUsernameIsUnique() {
        // Arrange
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userMapper.entityToResponse(any(UserEntity.class))).thenReturn(USER_RESPONSE);

        // Act
        UserResponse result = userService.createUser(USER_REQUEST);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo(USER_REQUEST.username());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldThrowUserExistsException_whenUsernameAlreadyExists() {
        // Arrange
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new UserEntity()));

        // Act & Assert
        assertThatThrownBy(() -> userService.createUser(USER_REQUEST))
                .isInstanceOf(UserExistsException.class)
                .hasMessageContaining("The username <<" + USER_REQUEST.username() + ">> already exists");

        verify(userRepository).findByUsername(USER_REQUEST.username());
    }
}
