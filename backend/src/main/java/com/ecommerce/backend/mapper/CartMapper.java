package com.ecommerce.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.backend.dto.request.CartRequestDTO;
import com.ecommerce.backend.dto.response.CartResponseDTO;
import com.ecommerce.backend.model.Cart;

@Mapper(componentModel = "spring", uses = { CartItemsMapper.class })
public interface CartMapper {

    @Mapping(source = "user.id", target = "userId")
    CartResponseDTO toCartResponseDTO(Cart cart);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "update_at", ignore = true)
    @Mapping(source = "user_id", target = "user.id")
    Cart toEntity(CartRequestDTO cartRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "update_at", ignore = true)
    @Mapping(source = "user_id", target = "user.id")
    void updateEntityFromRequestDto(CartRequestDTO dto, @MappingTarget Cart entity);
}

