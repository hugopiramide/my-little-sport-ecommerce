package com.ecommerce.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.dto.response.CartItemsResponseDTO;
import com.ecommerce.backend.model.CartItems;

@Mapper(componentModel = "spring", uses = { ProductVariantMapper.class })
public interface CartItemsMapper {

    @Mapping(source = "cart.id", target = "cartId")
    CartItemsResponseDTO toCartItemsResponseDTO(CartItems cartItems);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "cart_id", target = "cart.id")
    @Mapping(source = "product_variant_id", target = "productVariant.id")
    CartItems toEntity(CartItemsRequestDTO cartItemsRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "cart_id", target = "cart.id")
    @Mapping(source = "product_variant_id", target = "productVariant.id")
    void updateEntityFromRequestDto(CartItemsRequestDTO dto, @MappingTarget CartItems entity);
}

