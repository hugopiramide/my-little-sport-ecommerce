package com.ecommerce.backend.dto.response;

public record ProductVariantResponseDTO(
    Long id,
    Long productId,
    String size,
    Long stock,
    double priceModifier
) {}

