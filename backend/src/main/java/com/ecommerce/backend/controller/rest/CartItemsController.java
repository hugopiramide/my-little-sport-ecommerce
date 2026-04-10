package com.ecommerce.backend.controller.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.dto.response.CartItemsResponseDTO;
import com.ecommerce.backend.service.interfaces.CartItemsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemsController {

    private final CartItemsService cartItemsService;
    private final PagedResourcesAssembler<CartItemsResponseDTO> pagedResourcesAssembler;

    public CartItemsController(CartItemsService cartItemsService, PagedResourcesAssembler<CartItemsResponseDTO> pagedResourcesAssembler) {
        this.cartItemsService = cartItemsService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartItemsResponseDTO>> getAll() {
        return ResponseEntity.ok(cartItemsService.findAll());
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CartItemsResponseDTO>>> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<CartItemsResponseDTO> page = cartItemsService.findAllPageable(pageable);
        PagedModel<EntityModel<CartItemsResponseDTO>> pagedModel = pagedResourcesAssembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemsResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cartItemsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CartItemsResponseDTO> create(@Valid @RequestBody CartItemsRequestDTO dto) {
        CartItemsResponseDTO created = cartItemsService.createFromDto(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemsResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CartItemsRequestDTO dto) {
        return ResponseEntity.ok(cartItemsService.updateFromDto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cartItemsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

