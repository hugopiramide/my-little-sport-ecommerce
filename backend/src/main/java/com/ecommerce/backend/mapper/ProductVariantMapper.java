package com.ecommerce.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.backend.dto.request.ProductVariantRequestDTO;
import com.ecommerce.backend.dto.response.ProductVariantResponseDTO;
import com.ecommerce.backend.model.ProductVariant;

@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductVariantMapper {
    
    ProductVariantResponseDTO toProductVariantResponseDTO(ProductVariant productVariant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(source = "product_id", target = "product.id")
    @Mapping(source = "price_modifier", target = "priceModifier")
    ProductVariant toEntity(ProductVariantRequestDTO productVariantRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(source = "product_id", target = "product.id")
    @Mapping(source = "price_modifier", target = "priceModifier")
    void updateEntityFromRequestDto(ProductVariantRequestDTO dto, @MappingTarget ProductVariant entity);
}

