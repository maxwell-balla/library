package com.deep.library.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }

    public static BookNotFoundException forTitle(String title) {
        return new BookNotFoundException("The title <<" + title + ">> not found");
    }
}