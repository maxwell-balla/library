package com.deep.library.domains.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @Size(min = 3, max = 8, message = "The title must be between 3 and 8 characters")
        @NotBlank(message = "username is mandatory")
        String username
) {
}
