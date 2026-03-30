package com.ecommerce.backend.dto.response;

public record ProductResponseDTO(

    Long id,

    String name,

    String description,

    double basePrice,

    String imageUrl,

    boolean active

    // category
) {}
