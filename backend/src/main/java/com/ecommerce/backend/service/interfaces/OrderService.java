package com.ecommerce.backend.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.backend.dto.request.OrderRequestDTO;
import com.ecommerce.backend.dto.response.OrderResponseDTO;

public interface OrderService {

    List<OrderResponseDTO> findAll();

    Page<OrderResponseDTO> findAllPageable(Pageable pageable);

    OrderResponseDTO findById(Long id);

    OrderResponseDTO createFromDto(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO updateFromDto(Long id, OrderRequestDTO orderRequestDTO);

    void deleteById(Long id);
}

