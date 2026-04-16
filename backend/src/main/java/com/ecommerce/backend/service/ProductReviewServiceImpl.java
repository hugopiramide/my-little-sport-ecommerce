package com.ecommerce.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.ProductReviewRequestDTO;
import com.ecommerce.backend.dto.response.ProductReviewResponseDTO;
import com.ecommerce.backend.dto.response.ReviewSummaryResponseDTO;
import com.ecommerce.backend.mapper.ProductReviewMapper;
import com.ecommerce.backend.mapper.ReviewSummaryMapper;
import com.ecommerce.backend.model.ProductReview;
import com.ecommerce.backend.model.enums.ReviewStatus;
import com.ecommerce.backend.repository.ProductReviewRepository;
import com.ecommerce.backend.service.interfaces.ProductReviewService;

@Service
public class ProductReviewServiceImpl
        extends BaseCrudServiceImpl<ProductReview, ProductReviewResponseDTO, ProductReviewRequestDTO, ProductReviewRequestDTO>
        implements ProductReviewService {

    private final ProductReviewMapper productReviewMapper;
    private final ReviewSummaryMapper reviewSummaryMapper;

    public ProductReviewServiceImpl(ProductReviewRepository productReviewRepository, ProductReviewMapper productReviewMapper, ReviewSummaryMapper reviewSummaryMapper) {
        super(productReviewRepository);
        this.productReviewMapper = productReviewMapper;
        this.reviewSummaryMapper = reviewSummaryMapper;
    }

    @Override
    protected ProductReviewResponseDTO toDto(ProductReview entity) {
        return productReviewMapper.toResponseDTO(entity);
    }

    @Override
    protected List<ProductReviewResponseDTO> toDtoList(List<ProductReview> entities) {
        return entities.stream().map(productReviewMapper::toResponseDTO).toList();
    }

    @Override
    protected ProductReview toEntity(ProductReviewRequestDTO dto) {
        return productReviewMapper.toEntity(dto);
    }

    @Override
    protected void updateEntity(ProductReviewRequestDTO dto, ProductReview target) {
        productReviewMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected void updateEntityFromCreate(ProductReviewRequestDTO dto, ProductReview target) {
        productReviewMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected ProductReview newEntity() {
        return new ProductReview();
    }

    @Override
    protected String getEntityName() {
        return "ProductReview";
    }

    @Override
    protected void beforeCreate(ProductReviewRequestDTO dto, ProductReview entity) {
        boolean alreadyReviewed = ((ProductReviewRepository) repository).existsByUserIdAndProductIdAndOrderId(
                dto.userId(), dto.productId(), dto.orderId());
        if (alreadyReviewed) {
            throw new IllegalStateException(
                    "User " + dto.userId() + " has already reviewed product "
                    + dto.productId() + " for order " + dto.orderId());
        }
    }

    @Override
    public List<ProductReviewResponseDTO> findByProductId(Long productId) {
        return toDtoList(((ProductReviewRepository) repository).findByProductId(productId));
    }

    @Override
    public List<ProductReviewResponseDTO> findByProductIdAndStatus(Long productId, ReviewStatus status) {
        return toDtoList(((ProductReviewRepository) repository).findByProductIdAndStatus(productId, status));
    }

    @Override
    public List<ProductReviewResponseDTO> findByUserId(Long userId) {
        return toDtoList(((ProductReviewRepository) repository).findByUserId(userId));
    }

    @Override
    public List<ProductReviewResponseDTO> findByStatus(ReviewStatus status) {
        return toDtoList(((ProductReviewRepository) repository).findByStatus(status));
    }

    @Override
    public ReviewSummaryResponseDTO getSummaryByProductId(Long productId) {
        List<ProductReview> reviews = ((ProductReviewRepository) repository).findByProductIdAndStatus(
                productId, ReviewStatus.APPROVED);
        return reviewSummaryMapper.toDTO(reviews);
    }
}
