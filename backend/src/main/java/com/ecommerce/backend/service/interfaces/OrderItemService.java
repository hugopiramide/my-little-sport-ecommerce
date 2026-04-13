package com.ecommerce.backend.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.backend.dto.request.OrderItemRequestDTO;
import com.ecommerce.backend.dto.response.OrderItemResponseDTO;

public interface OrderItemService {

    List<OrderItemResponseDTO> findAll();

    Page<OrderItemResponseDTO> findAllPageable(Pageable pageable);

    OrderItemResponseDTO findById(Long id);

    OrderItemResponseDTO createFromDto(OrderItemRequestDTO orderItemRequestDTO);

    OrderItemResponseDTO updateFromDto(Long id, OrderItemRequestDTO orderItemRequestDTO);

    void deleteById(Long id);

    void clearProductVariantReferences(List<Long> variantIds);

    void clearProductVariantReference(Long variantId);
}

