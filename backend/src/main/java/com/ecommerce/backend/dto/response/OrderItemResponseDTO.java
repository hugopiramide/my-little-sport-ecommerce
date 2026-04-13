package com.ecommerce.backend.dto.response;

public record OrderItemResponseDTO(
    Long id,
    Long orderId,
    Long productVariantId,
    Long quantity,
    double price_at_purchase,
    // Data Snapshot
    String productName,
    String productSize,
    String productImageUrl,
    double basePrice,
    double priceModifier
) {}

