package com.ecommerce.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.backend.dto.request.OrderItemRequestDTO;
import com.ecommerce.backend.dto.response.OrderItemResponseDTO;
import com.ecommerce.backend.model.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "productVariant.id", target = "productVariantId")
    OrderItemResponseDTO toOrderItemResponseDTO(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "order_id", target = "order.id")
    @Mapping(source = "product_variant_id", target = "productVariant.id")
    OrderItem toEntity(OrderItemRequestDTO orderItemRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "order_id", target = "order.id")
    @Mapping(source = "product_variant_id", target = "productVariant.id")
    void updateEntityFromRequestDto(OrderItemRequestDTO dto, @MappingTarget OrderItem entity);
}

