package com.ecommerce.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.backend.dto.request.OrderRequestDTO;
import com.ecommerce.backend.dto.response.OrderResponseDTO;
import com.ecommerce.backend.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderResponseDTO toOrderResponseDTO(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order_date", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(source = "user_id", target = "user.id")
    Order toEntity(OrderRequestDTO orderRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order_date", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(source = "user_id", target = "user.id")
    void updateEntityFromRequestDto(OrderRequestDTO dto, @MappingTarget Order entity);
}

