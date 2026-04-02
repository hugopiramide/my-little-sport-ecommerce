package com.ecommerce.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.CartRequestDTO;
import com.ecommerce.backend.dto.response.CartResponseDTO;
import com.ecommerce.backend.mapper.CartMapper;
import com.ecommerce.backend.model.Cart;
import com.ecommerce.backend.repository.CartRepository;
import com.ecommerce.backend.service.interfaces.CartService;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public List<CartResponseDTO> findAll() {
        return cartRepository.findAll().stream()
                .map(cartMapper::toCartResponseDTO)
                .toList();
    }

    @Override
    public Page<CartResponseDTO> findAllPageable(Pageable pageable) {
        return cartRepository.findAll(pageable)
                .map(cartMapper::toCartResponseDTO);
    }

    @Override
    public CartResponseDTO findById(Long id) {
        return cartRepository.findById(id)
                .map(cartMapper::toCartResponseDTO)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));
    }

    @Override
    public CartResponseDTO createFromDto(CartRequestDTO cartRequestDTO) {
        Cart entity = new Cart();
        cartMapper.updateEntityFromRequestDto(cartRequestDTO, entity);
        entity.setUpdate_at(LocalDateTime.now());
        Cart saved = cartRepository.save(entity);
        return cartMapper.toCartResponseDTO(saved);
    }

    @Override
    public CartResponseDTO updateFromDto(Long id, CartRequestDTO cartRequestDTO) {
        Cart entity = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));
        cartMapper.updateEntityFromRequestDto(cartRequestDTO, entity);
        entity.setUpdate_at(LocalDateTime.now());
        Cart saved = cartRepository.save(entity);
        return cartMapper.toCartResponseDTO(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new RuntimeException("Cart not found with id: " + id);
        }
        cartRepository.deleteById(id);
    }
}

