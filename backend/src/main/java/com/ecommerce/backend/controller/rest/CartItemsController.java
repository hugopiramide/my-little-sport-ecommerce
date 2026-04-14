package com.ecommerce.backend.controller.rest;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.dto.response.CartItemsResponseDTO;
import com.ecommerce.backend.service.interfaces.CartItemsService;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemsController extends BaseRestController<CartItemsResponseDTO, CartItemsRequestDTO, CartItemsRequestDTO> {

    public CartItemsController(CartItemsService cartItemsService, PagedResourcesAssembler<CartItemsResponseDTO> pagedResourcesAssembler) {
        super(cartItemsService, pagedResourcesAssembler);
    }
}
