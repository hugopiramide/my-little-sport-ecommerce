package com.ecommerce.backend.dto.response;

import java.time.LocalDateTime;

import com.ecommerce.backend.model.enums.ReviewStatus;

public record ProductReviewResponseDTO(
    Long id,
    Integer rating,
    String title,
    String body,
    ReviewStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long userId,
    Long orderId,
    String userName,
    String userEmail,
    Long productId
) {}
