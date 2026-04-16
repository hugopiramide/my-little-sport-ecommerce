package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.CategoryRequestDTO;
import com.ecommerce.backend.dto.response.CategoryResponseDTO;

public interface CategoryService extends BaseCrudService<CategoryResponseDTO, CategoryRequestDTO, CategoryRequestDTO, Long> {
}
