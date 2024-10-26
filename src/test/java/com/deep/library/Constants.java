package com.deep.library;

import com.deep.library.domains.dto.BookRequest;
import com.deep.library.domains.dto.BookResponse;
import com.deep.library.domains.entities.BookEntity;

public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // BookRequest constants
    public static final String UNIQUE_TITLE = "Unique Title";
    public static final String DESCRIPTION = "Description";
    public static final BookRequest BOOK_REQUEST = new BookRequest(UNIQUE_TITLE, DESCRIPTION);
    public static final BookEntity BOOK_ENTITY = new BookEntity(1L, UNIQUE_TITLE, DESCRIPTION, true);
    public static final BookResponse BOOK_RESPONSE = new BookResponse(1L, UNIQUE_TITLE, DESCRIPTION);
}