package com.ecommerce.backend.dto.request;

import jakarta.validation.constraints.NotNull;

public record CartRequestDTO(

    @NotNull(message = "User is required")
    Long user_id

) {}

