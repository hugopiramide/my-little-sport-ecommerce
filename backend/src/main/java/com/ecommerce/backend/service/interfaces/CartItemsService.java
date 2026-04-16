package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.dto.response.CartItemsResponseDTO;

public interface CartItemsService extends BaseCrudService<CartItemsResponseDTO, CartItemsRequestDTO, CartItemsRequestDTO, Long> {
}
