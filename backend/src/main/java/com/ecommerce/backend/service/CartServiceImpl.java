package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.dto.request.CartRequestDTO;
import com.ecommerce.backend.dto.response.CartResponseDTO;
import com.ecommerce.backend.mapper.CartMapper;
import com.ecommerce.backend.model.Cart;
import com.ecommerce.backend.model.CartItems;
import com.ecommerce.backend.model.ProductVariant;
import com.ecommerce.backend.repository.CartItemsRepository;
import com.ecommerce.backend.repository.CartRepository;
import com.ecommerce.backend.repository.ProductVariantRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.interfaces.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl extends BaseCrudServiceImpl<Cart, CartResponseDTO, CartRequestDTO, CartRequestDTO> implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartItemsRepository cartItemsRepository;

    public CartServiceImpl(
            CartRepository cartRepository, 
            CartMapper cartMapper,
            UserRepository userRepository,
            ProductVariantRepository productVariantRepository,
            CartItemsRepository cartItemsRepository) {
        super(cartRepository);
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.userRepository = userRepository;
        this.productVariantRepository = productVariantRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    @Override
    @Transactional
    public CartResponseDTO addItem(Long userId, CartItemsRequestDTO itemDto) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found")));
                return cartRepository.save(newCart);
            });

        ProductVariant variant = productVariantRepository.findById(itemDto.product_variant_id())
            .orElseThrow(() -> new EntityNotFoundException("Product variant not found"));

        CartItems cartItem = cart.getCartItems().stream()
            .filter(item -> item.getProductVariant() != null && item.getProductVariant().getId().equals(variant.getId()))
            .findFirst()
            .orElse(null);

        long currentQuantity = (cartItem != null) ? cartItem.getQuantity() : 0L;
        long newQuantity = currentQuantity + itemDto.quantity();

        if (newQuantity > variant.getStock()) {
            throw new IllegalArgumentException("Not enough stock available. Current in cart: " + currentQuantity + ", Requested additional: " + itemDto.quantity() + ", Available: " + variant.getStock());
        }

        if (cartItem != null) {
            cartItem.setQuantity(newQuantity);
        } else {
            cartItem = new CartItems();
            cartItem.setCart(cart);
            cartItem.setProductVariant(variant);
            cartItem.setQuantity(newQuantity);
            cart.getCartItems().add(cartItem);
        }

        cartItemsRepository.save(cartItem);
        cart.setUpdate_at(LocalDateTime.now());
        cartRepository.save(cart);

        return toDto(cart);
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
