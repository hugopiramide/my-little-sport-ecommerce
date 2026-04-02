package com.ecommerce.backend.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.backend.dto.request.ProductVariantRequestDTO;
import com.ecommerce.backend.dto.response.ProductVariantResponseDTO;

public interface ProductVariantService {

    List<ProductVariantResponseDTO> findAll();

    Page<ProductVariantResponseDTO> findAllPageable(Pageable pageable);

    ProductVariantResponseDTO findById(Long id);

    ProductVariantResponseDTO createFromDto(ProductVariantRequestDTO productVariantRequestDTO);

    ProductVariantResponseDTO updateFromDto(Long id, ProductVariantRequestDTO productVariantRequestDTO);

    void deleteById(Long id);
}

