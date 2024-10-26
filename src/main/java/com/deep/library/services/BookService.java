package com.deep.library.services;

import com.deep.library.domains.dto.BookRequest;
import com.deep.library.domains.dto.BookResponse;

public interface BookService {
    BookResponse createBook(BookRequest dto);
}
