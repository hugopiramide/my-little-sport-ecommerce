package com.ecommerce.backend.controller.rest;

import java.util.List;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.ProductRequestDTO;
import com.ecommerce.backend.dto.response.ProductResponseDTO;
import com.ecommerce.backend.service.interfaces.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseRestController<ProductResponseDTO, ProductRequestDTO, ProductRequestDTO> {

    private final ProductService productService;

    public ProductController(ProductService productService, PagedResourcesAssembler<ProductResponseDTO> pagedResourcesAssembler) {
        super(productService, pagedResourcesAssembler);
        this.productService = productService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam String query) {
        return ResponseEntity.ok(productService.searchByNameAndDescription(query));
    }
}
