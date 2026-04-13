package com.ecommerce.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.dto.request.ProductRequestDTO;
import com.ecommerce.backend.dto.response.ProductResponseDTO;
import com.ecommerce.backend.mapper.ProductMapper;
import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.model.ProductVariant;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.service.interfaces.OrderItemService;
import com.ecommerce.backend.service.interfaces.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrderItemService orderItemService;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, OrderItemService orderItemService,
            CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.orderItemService = orderItemService;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public Page<ProductResponseDTO> findAllPageable(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toProductResponseDTO);
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponseDTO)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public ProductResponseDTO createFromDto(ProductRequestDTO productRequestDTO) {
        Product entity = new Product();
        productMapper.updateEntityFromRequestDto(productRequestDTO, entity);
        Category category = categoryRepository.findById((long) productRequestDTO.category_id())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + productRequestDTO.category_id()));
        entity.setCategory(category);
        Product savedProduct = productRepository.save(entity);
        return productMapper.toProductResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO updateFromDto(Long id, ProductRequestDTO productRequestDTO) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productMapper.updateEntityFromRequestDto(productRequestDTO, entity);
        entity.setActive(productRequestDTO.active());
        Category category = categoryRepository.findById((long) productRequestDTO.category_id())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + productRequestDTO.category_id()));
        entity.setCategory(category);
        Product savedProduct = productRepository.save(entity);
        return productMapper.toProductResponseDTO(savedProduct);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (product.getProductVariants() != null && !product.getProductVariants().isEmpty()) {
            List<Long> variantIds = product.getProductVariants().stream()
                    .map(ProductVariant::getId)
                    .collect(Collectors.toList());
            orderItemService.clearProductVariantReferences(variantIds);
        }
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public void nullifyCategory(Long categoryId) {
        productRepository.nullifyCategoryByCategoryId(categoryId);
    }

    @Override
    public List<ProductResponseDTO> searchByNameAndDescription(String query) {
        return productRepository.findByProductDataNameContainingIgnoreCaseOrProductDataDescriptionContainingIgnoreCase(query, query).stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }
}
