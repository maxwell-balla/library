package com.deep.library.services.impl;

import com.deep.library.domains.dto.HistoryResponse;
import com.deep.library.domains.entities.BookEntity;
import com.deep.library.domains.entities.HistoryEntity;
import com.deep.library.domains.entities.UserEntity;
import com.deep.library.domains.mappers.HistoryMapper;
import com.deep.library.repositories.BookRepository;
import com.deep.library.repositories.HistoryRepository;
import com.deep.library.services.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Slf4j
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    public HistoryServiceImpl(HistoryRepository historyRepository, HistoryMapper historyMapper) {
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public HistoryResponse saveHistory(UserEntity user, BookEntity book) {
        log.debug("save in history");
        HistoryEntity history = new HistoryEntity();
        history.setUser(user);
        history.setBook(book);
        history.setRecordDate(Instant.now());
        historyRepository.save(history);
        return historyMapper.entityToResponse(history);
    }
}
