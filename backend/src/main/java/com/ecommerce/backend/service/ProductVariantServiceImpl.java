package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.dto.request.ProductVariantRequestDTO;
import com.ecommerce.backend.dto.response.ProductVariantResponseDTO;
import com.ecommerce.backend.mapper.ProductVariantMapper;
import com.ecommerce.backend.model.ProductVariant;
import com.ecommerce.backend.repository.ProductVariantRepository;
import com.ecommerce.backend.service.interfaces.OrderItemService;
import com.ecommerce.backend.service.interfaces.ProductVariantService;

@Service
public class ProductVariantServiceImpl extends BaseCrudServiceImpl<ProductVariant, ProductVariantResponseDTO, ProductVariantRequestDTO, ProductVariantRequestDTO> implements ProductVariantService {

    private final OrderItemService orderItemService;
    private final ProductVariantMapper productVariantMapper;

    public ProductVariantServiceImpl(ProductVariantRepository productVariantRepository, @Lazy OrderItemService orderItemService, ProductVariantMapper productVariantMapper) {
        super(productVariantRepository);
        this.orderItemService = orderItemService;
        this.productVariantMapper = productVariantMapper;
    }

    @Override
    protected ProductVariantResponseDTO toDto(ProductVariant entity) {
        return productVariantMapper.toProductVariantResponseDTO(entity);
    }

    @Override
    protected List<ProductVariantResponseDTO> toDtoList(List<ProductVariant> entities) {
        return entities.stream().map(productVariantMapper::toProductVariantResponseDTO).toList();
    }

    @Override
    protected ProductVariant toEntity(ProductVariantRequestDTO dto) {
        return productVariantMapper.toEntity(dto);
    }

    @Override
    protected void updateEntity(ProductVariantRequestDTO dto, ProductVariant target) {
        productVariantMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected void updateEntityFromCreate(ProductVariantRequestDTO dto, ProductVariant target) {
        productVariantMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected ProductVariant newEntity() {
        return new ProductVariant();
    }

    @Override
    protected String getEntityName() {
        return "ProductVariant";
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw entityNotFoundException(id);
        }
        orderItemService.clearProductVariantReference(id);
        repository.deleteById(id);
    }

    @Override
    public ProductVariant findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> entityNotFoundException(id));
    }
}
