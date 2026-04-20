package com.ecommerce.backend.dto.response;

import java.time.LocalDateTime;

import com.ecommerce.backend.dto.ShippingAddressDTO;
import com.ecommerce.backend.model.enums.OrderStatus;

public record OrderResponseDTO(
    Long id,
    LocalDateTime order_date,
    OrderStatus status,
    double total_price,
    ShippingAddressDTO shippingAddress,
    Long userId,
    String userName,
    String userEmail
) {}
