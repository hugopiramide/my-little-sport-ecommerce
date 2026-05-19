package com.ecommerce.backend.controller.rest;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.dto.request.CartRequestDTO;
import com.ecommerce.backend.dto.response.CartResponseDTO;
import com.ecommerce.backend.service.interfaces.CartService;
import com.ecommerce.backend.model.User;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController extends BaseRestController<CartResponseDTO, CartRequestDTO, CartRequestDTO> {

    private final CartService cartService;

    public CartController(CartService cartService, PagedResourcesAssembler<CartResponseDTO> pagedResourcesAssembler) {
        super(cartService, pagedResourcesAssembler);
        this.cartService = cartService;
    }

    @PostMapping("/user/{userId}/add")
    public ResponseEntity<CartResponseDTO> addItemToUserCart(@PathVariable Long userId, @RequestBody CartItemsRequestDTO itemDto) {
        return ResponseEntity.ok(cartService.addItem(userId, itemDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponseDTO> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.findByUserId(userId));
    }

    @GetMapping("/user/count")
    public ResponseEntity<Long> getCartCountByUserId(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CartResponseDTO cart = cartService.findByUserId(user.getId());
        long count = 0;
        if (cart != null && cart.cartItems() != null) {
            for (com.ecommerce.backend.dto.response.CartItemsResponseDTO item : cart.cartItems()) {
                if (item.quantity() != null) {
                    count += item.quantity();
                }
            }
        }
        return ResponseEntity.ok(count);
    }
}
