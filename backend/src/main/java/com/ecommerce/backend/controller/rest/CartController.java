package com.ecommerce.backend.controller.rest;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.CartRequestDTO;
import com.ecommerce.backend.dto.response.CartResponseDTO;
import com.ecommerce.backend.service.interfaces.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController extends BaseRestController<CartResponseDTO, CartRequestDTO, CartRequestDTO> {

    public CartController(CartService cartService, PagedResourcesAssembler<CartResponseDTO> pagedResourcesAssembler) {
        super(cartService, pagedResourcesAssembler);
    }
}
