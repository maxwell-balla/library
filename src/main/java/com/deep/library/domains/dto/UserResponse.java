package com.deep.library.domains.dto;

import java.util.List;

public record UserResponse(
        Long id,
        String username,
        List<BookResponse> takenBooks
){
}