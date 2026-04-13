package com.ecommerce.backend.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.backend.dto.request.ProductRequestDTO;
import com.ecommerce.backend.dto.response.ProductResponseDTO;

public interface ProductService {

    public List<ProductResponseDTO> findAll();
    
    public Page<ProductResponseDTO> findAllPageable(Pageable pageable);

    public ProductResponseDTO findById(Long id);
    
    public ProductResponseDTO createFromDto(ProductRequestDTO productRequestDTO);

    public ProductResponseDTO updateFromDto(Long id, ProductRequestDTO productRequestDTO);

    public void deleteById(Long id);

    public void nullifyCategory(Long categoryId);

    public List<ProductResponseDTO> searchByNameAndDescription(String query);

}
