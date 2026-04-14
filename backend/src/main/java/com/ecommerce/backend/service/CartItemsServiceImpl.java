package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.dto.response.CartItemsResponseDTO;
import com.ecommerce.backend.mapper.CartItemsMapper;
import com.ecommerce.backend.model.CartItems;
import com.ecommerce.backend.repository.CartItemsRepository;
import com.ecommerce.backend.service.interfaces.CartItemsService;

@Service
public class CartItemsServiceImpl extends BaseCrudServiceImpl<CartItems, CartItemsResponseDTO, CartItemsRequestDTO, CartItemsRequestDTO> implements CartItemsService {

    private final CartItemsMapper cartItemsMapper;

    public CartItemsServiceImpl(CartItemsRepository cartItemsRepository, CartItemsMapper cartItemsMapper) {
        super(cartItemsRepository);
        this.cartItemsMapper = cartItemsMapper;
    }

    @Override
    protected CartItemsResponseDTO toDto(CartItems entity) {
        return cartItemsMapper.toCartItemsResponseDTO(entity);
    }

    @Override
    protected List<CartItemsResponseDTO> toDtoList(List<CartItems> entities) {
        return entities.stream().map(cartItemsMapper::toCartItemsResponseDTO).toList();
    }

    @Override
    protected CartItems toEntity(CartItemsRequestDTO dto) {
        return cartItemsMapper.toEntity(dto);
    }

    @Override
    protected void updateEntity(CartItemsRequestDTO dto, CartItems target) {
        cartItemsMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected void updateEntityFromCreate(CartItemsRequestDTO dto, CartItems target) {
        cartItemsMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected CartItems newEntity() {
        return new CartItems();
    }

    @Override
    protected String getEntityName() {
        return "CartItems";
    }
}
