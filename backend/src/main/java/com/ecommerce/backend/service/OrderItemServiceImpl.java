package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.dto.request.OrderItemRequestDTO;
import com.ecommerce.backend.dto.response.OrderItemResponseDTO;
import com.ecommerce.backend.mapper.OrderItemMapper;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.model.ProductVariant;
import com.ecommerce.backend.repository.OrderItemRepository;
import com.ecommerce.backend.service.interfaces.OrderItemService;
import com.ecommerce.backend.service.interfaces.ProductVariantService;

@Service
public class OrderItemServiceImpl extends BaseCrudServiceImpl<OrderItem, OrderItemResponseDTO, OrderItemRequestDTO, OrderItemRequestDTO> implements OrderItemService {

    private final OrderItemMapper orderItemMapper;
    private final ProductVariantService productVariantService;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, ProductVariantService productVariantService) {
        super(orderItemRepository);
        this.orderItemMapper = orderItemMapper;
        this.productVariantService = productVariantService;
    }

    @Override
    protected OrderItemResponseDTO toDto(OrderItem entity) {
        return orderItemMapper.toOrderItemResponseDTO(entity);
    }

    @Override
    protected List<OrderItemResponseDTO> toDtoList(List<OrderItem> entities) {
        return entities.stream().map(orderItemMapper::toOrderItemResponseDTO).toList();
    }

    @Override
    protected OrderItem toEntity(OrderItemRequestDTO dto) {
        return orderItemMapper.toEntity(dto);
    }

    @Override
    protected void updateEntity(OrderItemRequestDTO dto, OrderItem target) {
        orderItemMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected void updateEntityFromCreate(OrderItemRequestDTO dto, OrderItem target) {
        orderItemMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected OrderItem newEntity() {
        return new OrderItem();
    }

    @Override
    protected String getEntityName() {
        return "OrderItem";
    }

    @Override
    protected void afterCreate(OrderItemRequestDTO dto, OrderItem entity) {
        populateSnapshotFields(entity, dto.product_variant_id());
    }

    @Override
    @Transactional
    public void clearProductVariantReferences(List<Long> variantIds) {
        ((OrderItemRepository) repository).clearProductVariantReferences(variantIds);
    }

    @Override
    @Transactional
    public void clearProductVariantReference(Long variantId) {
        ((OrderItemRepository) repository).clearProductVariantReference(variantId);
    }

    @Override
    @Transactional
    public List<OrderItemResponseDTO> findByFilters(Long orderId, String productName) {
        String productNameFilter = (productName != null && !productName.trim().isEmpty()) ? productName.trim() : null;
        return toDtoList(((OrderItemRepository) repository).findByFilters(orderId, productNameFilter));
    }

    private void populateSnapshotFields(OrderItem entity, Long variantId) {
        if (variantId != null) {
            ProductVariant variant = productVariantService.findEntityById(variantId);

            entity.setProductName(variant.getProduct().getProductData().getName());
            entity.setProductSize(variant.getSize());
            entity.setProductImageUrl(variant.getProduct().getProductData().getImageUrl());
            entity.setBasePrice(variant.getProduct().getProductData().getBasePrice());
            entity.setPriceModifier(variant.getPriceModifier());

            double finalPrice = variant.getProduct().getProductData().getBasePrice() + variant.getPriceModifier();
            entity.setPrice_at_purchase(finalPrice);
        }
    }
}
