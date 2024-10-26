package com.deep.library.domains.mappers;

import com.deep.library.domains.dto.UserResponse;
import com.deep.library.domains.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "takenBooks", ignore = true)
    UserResponse entityToResponse(UserEntity user);
}