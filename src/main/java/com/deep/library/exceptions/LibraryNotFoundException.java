package com.deep.library.exceptions;

public class LibraryNotFoundException extends RuntimeException {
    public LibraryNotFoundException(String message) {
        super(message);
    }

    public static LibraryNotFoundException forBorrow(String borrowMessage) {
        return new LibraryNotFoundException(borrowMessage);
    }

    public static LibraryNotFoundException forReturn(String returnMessage) {
        return new LibraryNotFoundException(returnMessage);
    }
}
