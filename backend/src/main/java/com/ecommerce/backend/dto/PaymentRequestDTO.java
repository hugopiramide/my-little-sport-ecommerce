package com.ecommerce.backend.dto;

import java.math.BigDecimal;

public record PaymentRequestDTO(
    BigDecimal amount,
    String currency,
    String description,
    Long orderId
){}
