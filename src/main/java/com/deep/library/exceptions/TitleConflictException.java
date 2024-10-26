package com.deep.library.exceptions;

public class TitleConflictException extends RuntimeException {
    public TitleConflictException(String message) {
        super(message);
    }

    public static TitleConflictException forTitle(String title) {
        return new TitleConflictException("The title <<" + title + ">> already exists");
    }
}