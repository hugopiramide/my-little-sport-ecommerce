package com.ecommerce.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.backend.dto.request.ProductReviewRequestDTO;
import com.ecommerce.backend.dto.response.ProductReviewResponseDTO;
import com.ecommerce.backend.model.ProductReview;

@Mapper(componentModel = "spring")
public interface ProductReviewMapper {

    @Mapping(source = "user.id",                 target = "userId")
    @Mapping(source = "user.personalData.name",  target = "userName")
    @Mapping(source = "user.personalData.email", target = "userEmail")
    @Mapping(source = "product.id",              target = "productId")
    @Mapping(source = "order.id",                target = "orderId")
    ProductReviewResponseDTO toResponseDTO(ProductReview productReview);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "status",    ignore = true)   
    @Mapping(target = "createdAt", ignore = true)   
    @Mapping(target = "updatedAt", ignore = true)   
    @Mapping(source = "userId",    target = "user.id")
    @Mapping(source = "orderId",   target = "order.id")
    @Mapping(source = "productId", target = "product.id")
    ProductReview toEntity(ProductReviewRequestDTO dto);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "userId",    target = "user.id")
    @Mapping(source = "orderId",   target = "order.id")
    @Mapping(source = "productId", target = "product.id")
    void updateEntityFromRequestDto(ProductReviewRequestDTO dto, @MappingTarget ProductReview entity);
}
