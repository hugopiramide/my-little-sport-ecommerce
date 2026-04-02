package com.ecommerce.backend.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserFavoriteRequestDTO(

    @NotNull(message = "User is required")
    Long user_id,

    @NotNull(message = "Product is required")
    Long product_id,

    boolean notify_when_in_stock

) {}

