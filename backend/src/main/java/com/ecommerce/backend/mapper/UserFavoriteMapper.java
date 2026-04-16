package com.ecommerce.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.backend.dto.request.UserFavoriteRequestDTO;
import com.ecommerce.backend.dto.response.UserFavoriteResponseDTO;
import com.ecommerce.backend.model.UserFavorite;

@Mapper(componentModel = "spring")
public interface UserFavoriteMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "notify_when_in_stock", target = "notify_when_in_stock")
    UserFavoriteResponseDTO toUserFavoriteResponseDTO(UserFavorite userFavorite);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(source = "user_id", target = "user.id")
    @Mapping(source = "product_id", target = "product.id")
    @Mapping(source = "notify_when_in_stock", target = "notify_when_in_stock")
    UserFavorite toEntity(UserFavoriteRequestDTO userFavoriteRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(source = "user_id", target = "user.id")
    @Mapping(source = "product_id", target = "product.id")
    @Mapping(source = "notify_when_in_stock", target = "notify_when_in_stock")
    void updateEntityFromRequestDto(UserFavoriteRequestDTO dto, @MappingTarget UserFavorite entity);
}

