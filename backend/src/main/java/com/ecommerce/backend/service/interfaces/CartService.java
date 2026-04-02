package com.ecommerce.backend.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.backend.dto.request.CartRequestDTO;
import com.ecommerce.backend.dto.response.CartResponseDTO;

public interface CartService {

    List<CartResponseDTO> findAll();

    Page<CartResponseDTO> findAllPageable(Pageable pageable);

    CartResponseDTO findById(Long id);

    CartResponseDTO createFromDto(CartRequestDTO cartRequestDTO);

    CartResponseDTO updateFromDto(Long id, CartRequestDTO cartRequestDTO);

    void deleteById(Long id);
}

