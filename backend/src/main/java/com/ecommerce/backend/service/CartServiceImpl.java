package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.dto.request.CartRequestDTO;
import com.ecommerce.backend.dto.response.CartResponseDTO;
import com.ecommerce.backend.mapper.CartMapper;
import com.ecommerce.backend.model.Cart;
import com.ecommerce.backend.model.CartItems;
import com.ecommerce.backend.model.ProductVariant;
import com.ecommerce.backend.exception.InsufficientStockException;
import com.ecommerce.backend.repository.CartRepository;
import com.ecommerce.backend.service.interfaces.CartService;
import com.ecommerce.backend.service.interfaces.ProductVariantService;
import com.ecommerce.backend.service.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl extends BaseCrudServiceImpl<Cart, CartResponseDTO, CartRequestDTO, CartRequestDTO> implements CartService {
    private final CartMapper cartMapper;
    private final UserService userService;
    private final ProductVariantService productVariantService;

    public CartServiceImpl(
            CartRepository cartRepository, 
            CartMapper cartMapper,
            UserService userService,
            ProductVariantService productVariantService) {
        super(cartRepository);
        this.cartMapper = cartMapper;
        this.userService = userService;
        this.productVariantService = productVariantService;
    }

    @Override
    @Transactional
    public CartResponseDTO addItem(Long userId, CartItemsRequestDTO itemDto) {
        Cart cart = ((CartRepository) repository).findByUserId(userId)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(userService.getById(userId));
                return repository.save(newCart);
            });

        ProductVariant variant = productVariantService.findEntityById(itemDto.product_variant_id());

        CartItems cartItem = cart.getCartItems().stream()
            .filter(item -> item.getProductVariant() != null && item.getProductVariant().getId().equals(variant.getId()))
            .findFirst()
            .orElse(null);

        long currentQuantity = (cartItem != null) ? cartItem.getQuantity() : 0L;
        long newQuantity = currentQuantity + itemDto.quantity();

        if (newQuantity > variant.getStock()) {
            throw new InsufficientStockException("Not enough stock available.");
        }

        if (newQuantity <= 0) {
            if (cartItem != null) {
                cart.getCartItems().remove(cartItem);
            }
        } else {
            if (cartItem != null) {
                cartItem.setQuantity(newQuantity);
            } else {
                cartItem = new CartItems();
                cartItem.setCart(cart);
                cartItem.setProductVariant(variant);
                cartItem.setQuantity(newQuantity);
                cart.getCartItems().add(cartItem);
            }
        }

        cart.setUpdate_at(LocalDateTime.now());
        repository.save(cart);

        return toDto(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO findByUserId(Long userId) {
        Cart cart = ((CartRepository) repository).findByUserId(userId)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(userService.getById(userId));
                return repository.save(newCart);
            });
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