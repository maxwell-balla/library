package com.deep.library.domains.mappers;

import com.deep.library.domains.dto.BookResponse;
import com.deep.library.domains.entities.BookEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponse entityToResponse(BookEntity book);
}