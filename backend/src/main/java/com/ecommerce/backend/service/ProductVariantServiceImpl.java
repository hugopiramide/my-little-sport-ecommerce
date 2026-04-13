package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.dto.request.ProductVariantRequestDTO;
import com.ecommerce.backend.dto.response.ProductVariantResponseDTO;
import com.ecommerce.backend.mapper.ProductVariantMapper;
import com.ecommerce.backend.model.ProductVariant;
import com.ecommerce.backend.repository.OrderItemRepository;
import com.ecommerce.backend.repository.ProductVariantRepository;
import com.ecommerce.backend.service.interfaces.ProductVariantService;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductVariantMapper productVariantMapper;

    public ProductVariantServiceImpl(ProductVariantRepository productVariantRepository,
            OrderItemRepository orderItemRepository,
            ProductVariantMapper productVariantMapper) {
        this.productVariantRepository = productVariantRepository;
        this.orderItemRepository = orderItemRepository;
        this.productVariantMapper = productVariantMapper;
    }

    @Override
    public List<ProductVariantResponseDTO> findAll() {
        return productVariantRepository.findAll().stream()
                .map(productVariantMapper::toProductVariantResponseDTO)
                .toList();
    }

    @Override
    public Page<ProductVariantResponseDTO> findAllPageable(Pageable pageable) {
        return productVariantRepository.findAll(pageable)
                .map(productVariantMapper::toProductVariantResponseDTO);
    }

    @Override
    public ProductVariantResponseDTO findById(Long id) {
        return productVariantRepository.findById(id)
                .map(productVariantMapper::toProductVariantResponseDTO)
                .orElseThrow(() -> new RuntimeException("ProductVariant not found with id: " + id));
    }

    @Override
    public ProductVariantResponseDTO createFromDto(ProductVariantRequestDTO productVariantRequestDTO) {
        ProductVariant entity = new ProductVariant();
        productVariantMapper.updateEntityFromRequestDto(productVariantRequestDTO, entity);
        ProductVariant saved = productVariantRepository.save(entity);
        return productVariantMapper.toProductVariantResponseDTO(saved);
    }

    @Override
    public ProductVariantResponseDTO updateFromDto(Long id, ProductVariantRequestDTO productVariantRequestDTO) {
        ProductVariant entity = productVariantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductVariant not found with id: " + id));
        productVariantMapper.updateEntityFromRequestDto(productVariantRequestDTO, entity);
        ProductVariant saved = productVariantRepository.save(entity);
        return productVariantMapper.toProductVariantResponseDTO(saved);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!productVariantRepository.existsById(id)) {
            throw new RuntimeException("ProductVariant not found with id: " + id);
        }
        orderItemRepository.clearProductVariantReference(id);
        productVariantRepository.deleteById(id);
    }
}


