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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.ecommerce.backend.service.interfaces.OrderItemService;
import com.ecommerce.backend.service.interfaces.ProductService;

import org.springframework.data.domain.PageImpl;

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
    public List<ProductResponseDTO> searchByNameDescription(String query) {
        return ((ProductRepository) repository)
                .findByProductDataNameContainingIgnoreCaseOrProductDataDescriptionContainingIgnoreCase(query, query)
                .stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public Page<ProductResponseDTO> searchFiltered(String query, String category, String priceOrder, Pageable pageable) {
        Sort sort = Sort.unsorted();
        if ("low".equalsIgnoreCase(priceOrder)) {
            sort = Sort.by(ASC, "productData.basePrice");
        } else if ("high".equalsIgnoreCase(priceOrder)) {
            sort = Sort.by(DESC, "productData.basePrice");
        }

        List<Product> allProducts = ((ProductRepository) repository).findFiltered(query, category, sort);
        
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allProducts.size());
        
        List<ProductResponseDTO> pagedList = java.util.Collections.emptyList();
        if (start <= allProducts.size()) {
            pagedList = allProducts.subList(start, end)
                    .stream()
                    .map(productMapper::toProductResponseDTO)
                    .toList();
        }

        return new PageImpl<>(pagedList, pageable, allProducts.size());
    }

    private Category resolveCategory(int categoryId) {
        return categoryRepository.findById((long) categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
    }
}
