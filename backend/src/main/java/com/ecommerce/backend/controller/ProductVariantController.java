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
import org.springframework.web.bind.annotation.*;

import com.ecommerce.backend.dto.request.ProductVariantRequestDTO;
import com.ecommerce.backend.dto.response.ProductVariantResponseDTO;
import com.ecommerce.backend.service.interfaces.ProductVariantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product-variants")
public class ProductVariantController {

    private final ProductVariantService productVariantService;
    private final PagedResourcesAssembler<ProductVariantResponseDTO> pagedResourcesAssembler;

    public ProductVariantController(ProductVariantService productVariantService, PagedResourcesAssembler<ProductVariantResponseDTO> pagedResourcesAssembler) {
        this.productVariantService = productVariantService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductVariantResponseDTO>> getAll() {
        return ResponseEntity.ok(productVariantService.findAll());
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ProductVariantResponseDTO>>> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<ProductVariantResponseDTO> page = productVariantService.findAllPageable(pageable);
        PagedModel<EntityModel<ProductVariantResponseDTO>> pagedModel = pagedResourcesAssembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductVariantResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productVariantService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductVariantResponseDTO> create(@Valid @RequestBody ProductVariantRequestDTO dto) {
        ProductVariantResponseDTO created = productVariantService.createFromDto(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductVariantResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductVariantRequestDTO dto) {
        return ResponseEntity.ok(productVariantService.updateFromDto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productVariantService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

