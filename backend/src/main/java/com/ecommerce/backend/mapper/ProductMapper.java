package com.ecommerce.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.backend.dto.request.ProductRequestDTO;
import com.ecommerce.backend.dto.response.ProductResponseDTO;
import com.ecommerce.backend.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "productData.name", target = "name")
    @Mapping(source = "productData.description", target = "description")
    @Mapping(source = "productData.basePrice", target = "basePrice")
    @Mapping(source = "productData.imageUrl", target = "imageUrl")
    ProductResponseDTO toProductResponseDTO(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "productData.name")
    @Mapping(source = "description", target = "productData.description")
    @Mapping(source = "basePrice", target = "productData.basePrice")
    @Mapping(source = "imageUrl", target = "productData.imageUrl")
    Product toEntity(ProductRequestDTO productRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "productData.name")
    @Mapping(source = "description", target = "productData.description")
    @Mapping(source = "basePrice", target = "productData.basePrice")
    @Mapping(source = "imageUrl", target = "productData.imageUrl")
    void updateEntityFromRequestDto(ProductRequestDTO dto, @MappingTarget Product entity);

}
