package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.ProductVariantRequestDTO;
import com.ecommerce.backend.dto.response.ProductVariantResponseDTO;
import com.ecommerce.backend.model.ProductVariant;

import java.util.List;

public interface ProductVariantService extends BaseCrudService<ProductVariantResponseDTO, ProductVariantRequestDTO, ProductVariantRequestDTO, Long> {

    ProductVariant findEntityById(Long id);
    
    List<ProductVariantResponseDTO> findByFilters(Long productId, Long stockMin, Long stockMax, String size);
}
