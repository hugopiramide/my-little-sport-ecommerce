package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.ProductRequestDTO;
import com.ecommerce.backend.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductService extends BaseCrudService<ProductResponseDTO, ProductRequestDTO, ProductRequestDTO, Long> {

    void nullifyCategory(Long categoryId);

    List<ProductResponseDTO> searchByNameAndDescription(String query);
}
