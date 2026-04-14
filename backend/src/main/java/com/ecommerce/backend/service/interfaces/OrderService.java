package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.OrderRequestDTO;
import com.ecommerce.backend.dto.response.OrderResponseDTO;

public interface OrderService extends BaseCrudService<OrderResponseDTO, OrderRequestDTO, OrderRequestDTO, Long> {
}
