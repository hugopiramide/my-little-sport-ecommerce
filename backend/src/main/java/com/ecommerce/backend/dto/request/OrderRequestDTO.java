package com.ecommerce.backend.dto.request;

import com.ecommerce.backend.dto.ShippingAddressDTO;
import com.ecommerce.backend.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDTO(

    @NotNull(message = "User is required")
    Long user_id,

    @NotNull(message = "Status is required")
    OrderStatus status,

    double total_price,

    @NotNull(message = "Shipping address is required")
    ShippingAddressDTO shippingAddress

) {}
