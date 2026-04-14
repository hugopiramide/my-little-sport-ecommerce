package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.CartRequestDTO;
import com.ecommerce.backend.dto.response.CartResponseDTO;

public interface CartService extends BaseCrudService<CartResponseDTO, CartRequestDTO, CartRequestDTO, Long> {
}
