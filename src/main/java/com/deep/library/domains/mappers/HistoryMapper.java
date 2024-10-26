package com.deep.library.domains.mappers;

import com.deep.library.domains.dto.HistoryResponse;
import com.deep.library.domains.entities.HistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    @Mapping(target = "takenBy", source = "user.username")
    @Mapping(target = "titleBook", source = "book.title")
    HistoryResponse entityToResponse(HistoryEntity history);
}
