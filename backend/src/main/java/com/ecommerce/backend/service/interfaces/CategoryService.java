package com.ecommerce.backend.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.backend.dto.request.CategoryRequestDTO;
import com.ecommerce.backend.dto.response.CategoryResponseDTO;

public interface CategoryService {

    List<CategoryResponseDTO> findAll();

    Page<CategoryResponseDTO> findAllPageable(Pageable pageable);

    CategoryResponseDTO findById(Long id);

    CategoryResponseDTO createFromDto(CategoryRequestDTO categoryRequestDTO);

    CategoryResponseDTO updateFromDto(Long id, CategoryRequestDTO categoryRequestDTO);

    void deleteById(Long id);

}
