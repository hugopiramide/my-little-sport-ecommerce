package com.ecommerce.backend.dto.request;

import com.ecommerce.backend.model.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrderRequestDTO(

    @NotNull(message = "User is required")
    Long user_id,

    @NotNull(message = "Status is required")
    OrderStatus status,

    double total_price,

    @NotBlank(message = "Shipping address is required")
    @Size(max = 255, message = "Shipping address must be at most 255 characters")
    String shipping_addres

) {}
