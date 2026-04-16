package com.ecommerce.backend.dto.response;

import java.util.Map;

public record ReviewSummaryResponseDTO(
    double averageRating,
    long totalReviews,
    Map<Integer, Long> distribution,
    Map<Integer, Double> distributionPercentage
) {}
