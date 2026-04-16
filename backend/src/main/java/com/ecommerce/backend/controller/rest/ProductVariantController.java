package com.ecommerce.backend.controller.rest;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.ProductVariantRequestDTO;
import com.ecommerce.backend.dto.response.ProductVariantResponseDTO;
import com.ecommerce.backend.service.interfaces.ProductVariantService;

@RestController
@RequestMapping("/api/product-variants")
public class ProductVariantController extends BaseRestController<ProductVariantResponseDTO, ProductVariantRequestDTO, ProductVariantRequestDTO> {

    public ProductVariantController(ProductVariantService productVariantService, PagedResourcesAssembler<ProductVariantResponseDTO> pagedResourcesAssembler) {
        super(productVariantService, pagedResourcesAssembler);
    }
}
