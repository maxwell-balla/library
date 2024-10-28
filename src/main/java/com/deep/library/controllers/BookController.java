package com.deep.library.controllers;

import com.deep.library.domains.dto.BookRequest;
import com.deep.library.domains.dto.BookResponse;
import com.deep.library.exceptions.BookNotFoundException;
import com.deep.library.services.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book")
class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BookResponse> createBook(
            @RequestBody @Valid BookRequest dto
    ) {
        BookResponse newBook = bookService.createBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    @GetMapping
    Page<BookResponse> getBooks(Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/{title}")
    ResponseEntity<BookResponse> getBookById(@PathVariable String title) {
        return bookService.getBookById(title)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> BookNotFoundException.forTitle(title));
    }

    @DeleteMapping("{bookId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}