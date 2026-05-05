package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.ProductRequestDTO;
import com.ecommerce.backend.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService extends BaseCrudService<ProductResponseDTO, ProductRequestDTO, ProductRequestDTO, Long> {

    void nullifyCategory(Long categoryId);

    List<ProductResponseDTO> searchByNameAndDescription(String query);

    Page<ProductResponseDTO> searchFiltered(String query, String category, String priceOrder, Pageable pageable);
}
