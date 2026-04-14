package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.ProductVariantRequestDTO;
import com.ecommerce.backend.dto.response.ProductVariantResponseDTO;
import com.ecommerce.backend.model.ProductVariant;

public interface ProductVariantService extends BaseCrudService<ProductVariantResponseDTO, ProductVariantRequestDTO, ProductVariantRequestDTO, Long> {

    ProductVariant findEntityById(Long id);
}
