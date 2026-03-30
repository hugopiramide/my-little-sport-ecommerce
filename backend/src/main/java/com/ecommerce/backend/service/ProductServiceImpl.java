package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.ProductRequestDTO;
import com.ecommerce.backend.dto.response.ProductResponseDTO;
import com.ecommerce.backend.mapper.ProductMapper;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.service.interfaces.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public Page<ProductResponseDTO> findAllPageable(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toProductResponseDTO);
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponseDTO)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public ProductResponseDTO createFromDto(ProductRequestDTO productRequestDTO) {
        Product entity = new Product();
        productMapper.updateEntityFromRequestDto(productRequestDTO, entity);
        entity.setActive(true);
        Product savedProduct = productRepository.save(entity);
        return productMapper.toProductResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO updateFromDto(Long id, ProductRequestDTO productRequestDTO) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productMapper.updateEntityFromRequestDto(productRequestDTO, entity);
        entity.setActive(productRequestDTO.active());
        Product savedProduct = productRepository.save(entity);
        return productMapper.toProductResponseDTO(savedProduct);
    }

    @Override
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDTO> searchByName(String name) {
        return productRepository.findByProductDataNameContainingIgnoreCase(name).stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }
}
