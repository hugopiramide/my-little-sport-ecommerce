package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.OrderItemRequestDTO;
import com.ecommerce.backend.dto.response.OrderItemResponseDTO;

import java.util.List;

public interface OrderItemService extends BaseCrudService<OrderItemResponseDTO, OrderItemRequestDTO, OrderItemRequestDTO, Long> {

    void clearProductVariantReferences(List<Long> variantIds);

    void clearProductVariantReference(Long variantId);
}
