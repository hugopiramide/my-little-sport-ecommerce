package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.OrderItemRequestDTO;
import com.ecommerce.backend.dto.response.OrderItemResponseDTO;
import com.ecommerce.backend.mapper.OrderItemMapper;
import com.ecommerce.backend.model.OrderItem;
import com.ecommerce.backend.model.ProductVariant;
import com.ecommerce.backend.repository.OrderItemRepository;
import com.ecommerce.backend.repository.ProductVariantRepository;
import com.ecommerce.backend.service.interfaces.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ProductVariantRepository productVariantRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, ProductVariantRepository productVariantRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.productVariantRepository = productVariantRepository;
    }

    @Override
    public List<OrderItemResponseDTO> findAll() {
        return orderItemRepository.findAll().stream()
                .map(orderItemMapper::toOrderItemResponseDTO)
                .toList();
    }

    @Override
    public Page<OrderItemResponseDTO> findAllPageable(Pageable pageable) {
        return orderItemRepository.findAll(pageable)
                .map(orderItemMapper::toOrderItemResponseDTO);
    }

    @Override
    public OrderItemResponseDTO findById(Long id) {
        return orderItemRepository.findById(id)
                .map(orderItemMapper::toOrderItemResponseDTO)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + id));
    }

    @Override
    public OrderItemResponseDTO createFromDto(OrderItemRequestDTO orderItemRequestDTO) {
        OrderItem entity = new OrderItem();
        orderItemMapper.updateEntityFromRequestDto(orderItemRequestDTO, entity);
        
        // Capturar snapshot de la ProductVariant para historial desnormalizado
        if (orderItemRequestDTO.product_variant_id() != null) {
            ProductVariant variant = productVariantRepository.findById(orderItemRequestDTO.product_variant_id())
                    .orElseThrow(() -> new RuntimeException("ProductVariant not found with id: " + orderItemRequestDTO.product_variant_id()));
            
            // Guardar snapshot desnormalizado
            entity.setProductName(variant.getProduct().getProductData().getName());
            entity.setProductSize(variant.getSize());
            entity.setProductImageUrl(variant.getProduct().getProductData().getImageUrl());
            entity.setBasePrice(variant.getProduct().getProductData().getBasePrice());
            entity.setPriceModifier(variant.getPriceModifier());
            
            // Calcular precio final
            double finalPrice = variant.getProduct().getProductData().getBasePrice() + variant.getPriceModifier();
            entity.setPrice_at_purchase(finalPrice);
        }
        
        OrderItem saved = orderItemRepository.save(entity);
        return orderItemMapper.toOrderItemResponseDTO(saved);
    }

    @Override
    public OrderItemResponseDTO updateFromDto(Long id, OrderItemRequestDTO orderItemRequestDTO) {
        OrderItem entity = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + id));
        orderItemMapper.updateEntityFromRequestDto(orderItemRequestDTO, entity);
        OrderItem saved = orderItemRepository.save(entity);
        return orderItemMapper.toOrderItemResponseDTO(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new RuntimeException("OrderItem not found with id: " + id);
        }
        orderItemRepository.deleteById(id);
    }
}

