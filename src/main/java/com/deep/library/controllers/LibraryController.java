package com.deep.library.controllers;

import com.deep.library.domains.dto.HistoryResponse;
import com.deep.library.services.LibraryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/library")
@Tag(name = "library")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/borrow")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    ResponseEntity<HistoryResponse> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        HistoryResponse newHistory = libraryService.borrowBook(userId, bookId);
        return ResponseEntity.ok().body(newHistory);
    }

    @PostMapping("/return")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    ResponseEntity<HistoryResponse> returnBook(@RequestParam Long historyId) {
        HistoryResponse history = libraryService.returnBook(historyId);
        return ResponseEntity.ok().body(history);
    }
}
