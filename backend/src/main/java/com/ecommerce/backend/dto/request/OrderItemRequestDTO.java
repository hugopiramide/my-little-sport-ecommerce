package com.ecommerce.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDTO(

    @NotNull(message = "Order is required")
    Long order_id,

    @NotNull(message = "Product variant is required")
    Long product_variant_id,

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be 1 or greater")
    Long quantity,

    double price_at_purchase

) {}

