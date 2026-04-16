package com.ecommerce.backend.service.interfaces;

import java.util.List;

import com.ecommerce.backend.dto.request.ProductReviewRequestDTO;
import com.ecommerce.backend.dto.response.ProductReviewResponseDTO;
import com.ecommerce.backend.dto.response.ReviewSummaryResponseDTO;
import com.ecommerce.backend.model.enums.ReviewStatus;

public interface ProductReviewService
        extends BaseCrudService<ProductReviewResponseDTO, ProductReviewRequestDTO, ProductReviewRequestDTO, Long> {

    List<ProductReviewResponseDTO> findByProductId(Long productId);

    List<ProductReviewResponseDTO> findByProductIdAndStatus(Long productId, ReviewStatus status);

    List<ProductReviewResponseDTO> findByUserId(Long userId);

    List<ProductReviewResponseDTO> findByStatus(ReviewStatus status);

    ReviewSummaryResponseDTO getSummaryByProductId(Long productId);
}
