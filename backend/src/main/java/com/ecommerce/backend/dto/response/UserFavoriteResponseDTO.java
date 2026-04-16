package com.ecommerce.backend.dto.response;

import java.time.LocalDateTime;

public record UserFavoriteResponseDTO(
    Long id,
    Long userId,
    Long productId,
    boolean notify_when_in_stock,
    LocalDateTime created_at
) {}

