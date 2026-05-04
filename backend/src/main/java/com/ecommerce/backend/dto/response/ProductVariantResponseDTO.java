package com.ecommerce.backend.dto.response;

public record ProductVariantResponseDTO(
    Long id,
    ProductResponseDTO product,
    String size,
    Long stock,
    double priceModifier
) {}

