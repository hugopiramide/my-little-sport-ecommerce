package com.ecommerce.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductVariantRequestDTO(

    @NotNull(message = "Product is required")
    Long product_id,

    @NotBlank(message = "Size is required")
    @Size(max = 10, message = "Size must be at most 10 characters")
    String size,

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be 0 or greater")
    Long stock,

    @NotNull(message = "Price modifier is required")
    @Min(value = 0, message = "Price modifier must be 0 or greater")
    double price_modifier

) {}

