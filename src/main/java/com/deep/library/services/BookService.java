package com.deep.library.services;

import com.deep.library.domains.dto.BookRequest;
import com.deep.library.domains.dto.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse createBook(BookRequest dto);

    Page<BookResponse> getAllBooks(Pageable pageable);
}
