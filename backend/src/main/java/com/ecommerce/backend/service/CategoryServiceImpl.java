package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.dto.request.CategoryRequestDTO;
import com.ecommerce.backend.dto.response.CategoryResponseDTO;
import com.ecommerce.backend.mapper.CategoryMapper;
import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.service.interfaces.CategoryService;
import com.ecommerce.backend.service.interfaces.ProductService;

@Service
public class CategoryServiceImpl extends BaseCrudServiceImpl<Category, CategoryResponseDTO, CategoryRequestDTO, CategoryRequestDTO> implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, ProductService productService) {
        super(categoryRepository);
        this.categoryMapper = categoryMapper;
        this.productService = productService;
    }

    @Override
    protected CategoryResponseDTO toDto(Category entity) {
        return categoryMapper.toCategoryResponseDTO(entity);
    }

    @Override
    protected List<CategoryResponseDTO> toDtoList(List<Category> entities) {
        return entities.stream().map(categoryMapper::toCategoryResponseDTO).toList();
    }

    @Override
    protected Category toEntity(CategoryRequestDTO dto) {
        return categoryMapper.toEntity(dto);
    }

    @Override
    protected void updateEntity(CategoryRequestDTO dto, Category target) {
        categoryMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected void updateEntityFromCreate(CategoryRequestDTO dto, Category target) {
        categoryMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected Category newEntity() {
        return new Category();
    }

    @Override
    protected String getEntityName() {
        return "Category";
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw entityNotFoundException(id);
        }
        productService.nullifyCategory(id);
        repository.deleteById(id);
    }
}
