package com.deep.library;

import com.deep.library.domains.dto.BookRequest;
import com.deep.library.domains.dto.BookResponse;
import com.deep.library.domains.dto.UserRequest;
import com.deep.library.domains.dto.UserResponse;
import com.deep.library.domains.entities.BookEntity;

public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Book constants
    public static final String UNIQUE_TITLE = "Unique Title";
    public static final String DESCRIPTION = "Description";
    public static final BookRequest BOOK_REQUEST = new BookRequest(UNIQUE_TITLE, DESCRIPTION);
    public static final BookEntity BOOK_ENTITY = new BookEntity(1L, UNIQUE_TITLE, DESCRIPTION, true);
    public static final BookResponse BOOK_RESPONSE = new BookResponse(1L, UNIQUE_TITLE, DESCRIPTION);

    // User constants
    public static final String UNIQUE_USERNAME = "Unique Username";
    public static final UserRequest USER_REQUEST = new UserRequest(UNIQUE_USERNAME);
    public static final UserResponse USER_RESPONSE = new UserResponse(1L, UNIQUE_USERNAME);
}