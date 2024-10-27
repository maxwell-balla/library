package com.deep.library.services;

import com.deep.library.domains.dto.HistoryResponse;
import com.deep.library.domains.entities.BookEntity;
import com.deep.library.domains.entities.UserEntity;

public interface HistoryService {

    HistoryResponse saveHistory(UserEntity userEntity, BookEntity bookEntity);
}
