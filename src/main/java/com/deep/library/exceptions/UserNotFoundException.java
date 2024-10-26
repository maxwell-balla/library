package com.deep.library.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException forId(Long id) {
        return new UserNotFoundException("User not found with id " + id);
    }
}
