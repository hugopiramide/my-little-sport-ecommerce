package com.ecommerce.backend.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequestDTO(

    @NotBlank(message = "Name is required")
    String name,

    @Max(255)
    String description,

    @NotNull(message = "Price is required")
    double basePrice,

    @Max(255)
    String imageUrl,

    boolean active,

    @NotBlank(message = "Category is required")
    int category_id

) {}
