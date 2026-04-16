package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.OrderRequestDTO;
import com.ecommerce.backend.dto.response.OrderResponseDTO;
import com.ecommerce.backend.model.enums.OrderStatus;

import java.util.List;

public interface OrderService extends BaseCrudService<OrderResponseDTO, OrderRequestDTO, OrderRequestDTO, Long> {

    List<OrderResponseDTO> findByFilters(String dateFrom, String dateTo, OrderStatus status);
}
