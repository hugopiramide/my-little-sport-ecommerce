package com.ecommerce.backend.controller.rest;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.dto.request.CartRequestDTO;
import com.ecommerce.backend.dto.response.CartResponseDTO;
import com.ecommerce.backend.service.interfaces.CartService;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
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
}
