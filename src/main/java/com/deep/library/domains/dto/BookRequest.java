package com.deep.library.domains.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookRequest(

        @Size(min = 2, max = 100, message = "The title must be between 1 and 100 characters")
        @NotBlank(message = "title is mandatory")
        String title,

        @Size(max = 1000, message = "The description may not exceed 1000 characters")
        String description
) {}
