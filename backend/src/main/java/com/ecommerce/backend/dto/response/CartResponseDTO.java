package com.ecommerce.backend.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CartResponseDTO(
    Long id,
    Long userId,
    LocalDateTime update_at,
    List<CartItemsResponseDTO> cartItems
) {}

