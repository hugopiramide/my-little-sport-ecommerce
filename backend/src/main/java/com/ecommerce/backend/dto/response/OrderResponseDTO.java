package com.ecommerce.backend.dto.response;

import java.time.LocalDateTime;

public record OrderResponseDTO(
    Long id,
    LocalDateTime order_date,
    String status,
    double total_price,
    String shipping_addres,
    Long userId
) {}

