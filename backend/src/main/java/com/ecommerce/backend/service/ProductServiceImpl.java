package com.ecommerce.backend.service;

import java.util.List;
import java.util.stream.Collectors;

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
public class ProductServiceImpl extends BaseCrudServiceImpl<Product, ProductResponseDTO, ProductRequestDTO, ProductRequestDTO> implements ProductService {

    private final OrderItemService orderItemService;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, OrderItemService orderItemService, CategoryRepository categoryRepository, ProductMapper productMapper) {
        super(productRepository);
        this.orderItemService = orderItemService;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    protected ProductResponseDTO toDto(Product entity) {
        return productMapper.toProductResponseDTO(entity);
    }

    @Override
    protected List<ProductResponseDTO> toDtoList(List<Product> entities) {
        return entities.stream().map(productMapper::toProductResponseDTO).toList();
    }

    @Override
    protected Product toEntity(ProductRequestDTO dto) {
        return productMapper.toEntity(dto);
    }

    @Override
    protected void updateEntity(ProductRequestDTO dto, Product target) {
        productMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected void updateEntityFromCreate(ProductRequestDTO dto, Product target) {
        productMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected Product newEntity() {
        return new Product();
    }

    @Override
    protected String getEntityName() {
        return "Product";
    }

    @Override
    protected void afterCreate(ProductRequestDTO dto, Product entity) {
        Category category = resolveCategory(dto.category_id());
        entity.setCategory(category);
    }

    @Override
    protected void afterUpdate(ProductRequestDTO dto, Product entity) {
        entity.setActive(dto.active());
        Category category = resolveCategory(dto.category_id());
        entity.setCategory(category);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Product product = findEntityById(id);

        if (product.getProductVariants() != null && !product.getProductVariants().isEmpty()) {
            List<Long> variantIds = product.getProductVariants().stream()
                    .map(ProductVariant::getId)
                    .collect(Collectors.toList());
            orderItemService.clearProductVariantReferences(variantIds);
        }
        repository.delete(product);
    }

    @Override
    @Transactional
    public void nullifyCategory(Long categoryId) {
        ((ProductRepository) repository).nullifyCategoryByCategoryId(categoryId);
    }

    @Override
    public List<ProductResponseDTO> searchByNameAndDescription(String query) {
        return ((ProductRepository) repository)
                .findByProductDataNameContainingIgnoreCaseOrProductDataDescriptionContainingIgnoreCase(query, query)
                .stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }

    private Category resolveCategory(int categoryId) {
        return categoryRepository.findById((long) categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
    }
}
