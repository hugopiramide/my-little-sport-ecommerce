package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.CategoryRequestDTO;
import com.ecommerce.backend.dto.response.CategoryResponseDTO;
import com.ecommerce.backend.mapper.CategoryMapper;
import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.service.interfaces.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponseDTO)
                .toList();
    }

    @Override
    public Page<CategoryResponseDTO> findAllPageable(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toCategoryResponseDTO);
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return categoryMapper.toCategoryResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO createFromDto(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.toEntity(categoryRequestDTO);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDTO(saved);
    }

    @Override
    public CategoryResponseDTO updateFromDto(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        categoryMapper.updateEntityFromRequestDto(categoryRequestDTO, category);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDTO(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

}
