package com.deep.library.services.impl;

import com.deep.library.domains.dto.HistoryResponse;
import com.deep.library.domains.entities.BookEntity;
import com.deep.library.domains.entities.HistoryEntity;
import com.deep.library.domains.entities.UserEntity;
import com.deep.library.domains.mappers.HistoryMapper;
import com.deep.library.exceptions.LibraryNotFoundException;
import com.deep.library.repositories.BookRepository;
import com.deep.library.repositories.HistoryRepository;
import com.deep.library.repositories.UserRepository;
import com.deep.library.services.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
public class LibraryServiceImpl implements LibraryService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    public LibraryServiceImpl(UserRepository userRepository, BookRepository bookRepository, HistoryRepository historyRepository, HistoryMapper historyMapper) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public HistoryResponse borrowBook(Long userId, Long bookId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        Optional<BookEntity> book = bookRepository.findById(bookId);

        if (user.isPresent() && book.isPresent()) {
            if (!book.get().isAvailable()) {
                throw new LibraryNotFoundException("The book is currently unavailable");
            }
            return saveHistoryAndUpdateBook(user.get(), book.get());
        }
        throw LibraryNotFoundException.forBorrow("User or Book not found");
    }

    public HistoryResponse saveHistoryAndUpdateBook(UserEntity user, BookEntity book) {
        HistoryEntity history = setHistoryAndSave(user, book);
        updateBookStatus(book);
        return historyMapper.entityToResponse(history);
    }

    public HistoryEntity setHistoryAndSave(UserEntity user, BookEntity book) {
        HistoryEntity history = new HistoryEntity();
        history.setUser(user);
        history.setBook(book);
        history.setRecordDate(Instant.now());
        historyRepository.save(history);
        return history;
    }

    private void updateBookStatus(BookEntity book) {
        book.setAvailable(false);
        bookRepository.save(book);
    }

    @Override
    public HistoryResponse returnBook(Long historyId) {
        HistoryEntity history = historyRepository.findById(historyId)
                .orElseThrow(() -> LibraryNotFoundException.forReturn("History record not found"));

        history.setRecordDate(Instant.now());
        return historyMapper.entityToResponse(historyRepository.save(history));
    }
}