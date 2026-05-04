package com.ecommerce.backend.dto.response;

public record CartItemsResponseDTO(
    Long id,
    Long cartId,
    ProductVariantResponseDTO productVariantId,
    Long quantity
) {}

