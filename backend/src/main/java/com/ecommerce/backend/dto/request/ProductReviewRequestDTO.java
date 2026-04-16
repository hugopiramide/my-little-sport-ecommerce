package com.ecommerce.backend.dto.request;

import com.ecommerce.backend.model.enums.ReviewStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductReviewRequestDTO(

    @NotNull(message = "User is required")
    Long userId,

    @NotNull(message = "Order is required")
    Long orderId,

    @NotNull(message = "Product is required")
    Long productId,

    @Size(max = 150, message = "Title must not exceed 150 characters")
    String title,

    String body,

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    Integer rating,

    ReviewStatus status
) {}
