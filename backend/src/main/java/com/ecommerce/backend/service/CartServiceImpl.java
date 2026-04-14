package com.ecommerce.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.CartRequestDTO;
import com.ecommerce.backend.dto.response.CartResponseDTO;
import com.ecommerce.backend.mapper.CartMapper;
import com.ecommerce.backend.model.Cart;
import com.ecommerce.backend.repository.CartRepository;
import com.ecommerce.backend.service.interfaces.CartService;

@Service
public class CartServiceImpl extends BaseCrudServiceImpl<Cart, CartResponseDTO, CartRequestDTO, CartRequestDTO> implements CartService {

    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        super(cartRepository);
        this.cartMapper = cartMapper;
    }

    @Override
    protected CartResponseDTO toDto(Cart entity) {
        return cartMapper.toCartResponseDTO(entity);
    }

    @Override
    protected List<CartResponseDTO> toDtoList(List<Cart> entities) {
        return entities.stream().map(cartMapper::toCartResponseDTO).toList();
    }

    @Override
    protected Cart toEntity(CartRequestDTO dto) {
        return cartMapper.toEntity(dto);
    }

    @Override
    protected void updateEntity(CartRequestDTO dto, Cart target) {
        cartMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected void updateEntityFromCreate(CartRequestDTO dto, Cart target) {
        cartMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected Cart newEntity() {
        return new Cart();
    }

    @Override
    protected String getEntityName() {
        return "Cart";
    }

    @Override
    protected void afterCreate(CartRequestDTO dto, Cart entity) {
        entity.setUpdate_at(LocalDateTime.now());
    }

    @Override
    protected void afterUpdate(CartRequestDTO dto, Cart entity) {
        entity.setUpdate_at(LocalDateTime.now());
    }
}
