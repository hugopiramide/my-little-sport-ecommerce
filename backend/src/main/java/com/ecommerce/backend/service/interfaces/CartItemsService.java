package com.ecommerce.backend.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.dto.response.CartItemsResponseDTO;

public interface CartItemsService {

    List<CartItemsResponseDTO> findAll();

    Page<CartItemsResponseDTO> findAllPageable(Pageable pageable);

    CartItemsResponseDTO findById(Long id);

    CartItemsResponseDTO createFromDto(CartItemsRequestDTO cartItemsRequestDTO);

    CartItemsResponseDTO updateFromDto(Long id, CartItemsRequestDTO cartItemsRequestDTO);

    void deleteById(Long id);
}

