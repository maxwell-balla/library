package com.deep.library.services.impl;

import com.deep.library.domains.dto.BookRequest;
import com.deep.library.domains.dto.BookResponse;
import com.deep.library.domains.entities.BookEntity;
import com.deep.library.domains.mappers.BookMapper;
import com.deep.library.exceptions.TitleConflictException;
import com.deep.library.repositories.BookRepository;
import com.deep.library.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BookResponse createBook(BookRequest dto) {
        log.debug("Request to save Book : {}", dto);
        if (Boolean.TRUE.equals(bookRepository.existsByTitle(dto.title()))) {
            throw TitleConflictException.forTitle(dto.title());
        }
        BookEntity book = new BookEntity();
        book.setTitle(dto.title());
        book.setDescription(dto.description());
        bookRepository.save(book);

        return bookMapper.entityToResponse(book);
    }
}
