package com.ecommerce.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.backend.dto.request.ShippingAddressRequestDTO;
import com.ecommerce.backend.dto.response.ShippingAddressResponseDTO;
import com.ecommerce.backend.model.vo.ShippingAddress;

@Mapper(componentModel = "spring")
public interface ShippingAddressMapper {

    ShippingAddress toEntity(ShippingAddressRequestDTO request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "userId")
    ShippingAddressResponseDTO toResponseDTO(ShippingAddress entity, int id, Long userId);

    void updateEntityFromRequest(ShippingAddressRequestDTO request, @MappingTarget ShippingAddress entity);
}
