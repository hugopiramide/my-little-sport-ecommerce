package com.ecommerce.backend.controller;

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

import com.ecommerce.backend.dto.request.CartRequestDTO;
import com.ecommerce.backend.dto.response.CartResponseDTO;
import com.ecommerce.backend.service.interfaces.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;
    private final PagedResourcesAssembler<CartResponseDTO> pagedResourcesAssembler;

    public CartController(CartService cartService, PagedResourcesAssembler<CartResponseDTO> pagedResourcesAssembler) {
        this.cartService = cartService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartResponseDTO>> getAll() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CartResponseDTO>>> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<CartResponseDTO> page = cartService.findAllPageable(pageable);
        PagedModel<EntityModel<CartResponseDTO>> pagedModel = pagedResourcesAssembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CartResponseDTO> create(@Valid @RequestBody CartRequestDTO dto) {
        CartResponseDTO created = cartService.createFromDto(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CartRequestDTO dto) {
        return ResponseEntity.ok(cartService.updateFromDto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cartService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

