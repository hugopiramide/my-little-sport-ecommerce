package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.dto.response.CartItemsResponseDTO;
import com.ecommerce.backend.mapper.CartItemsMapper;
import com.ecommerce.backend.model.CartItems;
import com.ecommerce.backend.repository.CartItemsRepository;
import com.ecommerce.backend.service.interfaces.CartItemsService;

@Service
public class CartItemsServiceImpl implements CartItemsService {

    private final CartItemsRepository cartItemsRepository;
    private final CartItemsMapper cartItemsMapper;

    public CartItemsServiceImpl(CartItemsRepository cartItemsRepository, CartItemsMapper cartItemsMapper) {
        this.cartItemsRepository = cartItemsRepository;
        this.cartItemsMapper = cartItemsMapper;
    }

    @Override
    public List<CartItemsResponseDTO> findAll() {
        return cartItemsRepository.findAll().stream()
                .map(cartItemsMapper::toCartItemsResponseDTO)
                .toList();
    }

    @Override
    public Page<CartItemsResponseDTO> findAllPageable(Pageable pageable) {
        return cartItemsRepository.findAll(pageable)
                .map(cartItemsMapper::toCartItemsResponseDTO);
    }

    @Override
    public CartItemsResponseDTO findById(Long id) {
        return cartItemsRepository.findById(id)
                .map(cartItemsMapper::toCartItemsResponseDTO)
                .orElseThrow(() -> new RuntimeException("CartItems not found with id: " + id));
    }

    @Override
    public CartItemsResponseDTO createFromDto(CartItemsRequestDTO cartItemsRequestDTO) {
        CartItems entity = new CartItems();
        cartItemsMapper.updateEntityFromRequestDto(cartItemsRequestDTO, entity);
        CartItems saved = cartItemsRepository.save(entity);
        return cartItemsMapper.toCartItemsResponseDTO(saved);
    }

    @Override
    public CartItemsResponseDTO updateFromDto(Long id, CartItemsRequestDTO cartItemsRequestDTO) {
        CartItems entity = cartItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItems not found with id: " + id));
        cartItemsMapper.updateEntityFromRequestDto(cartItemsRequestDTO, entity);
        CartItems saved = cartItemsRepository.save(entity);
        return cartItemsMapper.toCartItemsResponseDTO(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!cartItemsRepository.existsById(id)) {
            throw new RuntimeException("CartItems not found with id: " + id);
        }
        cartItemsRepository.deleteById(id);
    }
}

