package com.deep.library.services;

import com.deep.library.domains.dto.HistoryResponse;
import com.deep.library.domains.entities.BookEntity;
import com.deep.library.domains.entities.HistoryEntity;
import com.deep.library.domains.entities.UserEntity;

public interface LibraryService {
    HistoryResponse borrowBook(Long userId, Long bookId);

    HistoryResponse returnBook(Long historyId);

    HistoryEntity setHistoryAndSave(UserEntity userEntity, BookEntity bookEntity);
}
