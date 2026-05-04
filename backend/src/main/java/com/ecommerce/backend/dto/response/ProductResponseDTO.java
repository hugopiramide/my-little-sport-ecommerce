package com.ecommerce.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductResponseDTO(

    Long id,

    String name,

    String description,

    double basePrice,

    String imageUrl,

    boolean active,

    @JsonProperty("categoryName")
    String categoryName
) {}
