package com.deep.library.domains.dto;

import lombok.Builder;

@Builder
public record RefreshResponse(
        String accessToken
) {
}
