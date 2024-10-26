package com.deep.library.domains.dto;

public record BookResponse(
        Long id,
        String title,
        String description
){}