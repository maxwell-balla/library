package com.deep.library.exceptions;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }

    public static UserExistsException forUsername(String username) {
        return new UserExistsException("The username <<" + username + ">> already exists");
    }
}