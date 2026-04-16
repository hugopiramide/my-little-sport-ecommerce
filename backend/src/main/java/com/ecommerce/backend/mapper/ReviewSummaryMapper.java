package com.ecommerce.backend.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ecommerce.backend.dto.response.ReviewSummaryResponseDTO;
import com.ecommerce.backend.model.ProductReview;

@Component
public class ReviewSummaryMapper {

    public ReviewSummaryResponseDTO toDTO(List<ProductReview> reviews) {
        long total = reviews.size();

        if (total == 0) {
            Map<Integer, Long> emptyDist = Map.of(1, 0L, 2, 0L, 3, 0L, 4, 0L, 5, 0L);
            Map<Integer, Double> emptyPct = Map.of(1, 0.0, 2, 0.0, 3, 0.0, 4, 0.0, 5, 0.0);
            return new ReviewSummaryResponseDTO(0.0, 0L, emptyDist, emptyPct);
        }

        Map<Integer, Long> distribution = reviews.stream()
                .collect(Collectors.groupingBy(ProductReview::getRating, Collectors.counting()));

        for (int star = 1; star <= 5; star++) {
            distribution.putIfAbsent(star, 0L);
        }

        double average = reviews.stream()
                .mapToInt(ProductReview::getRating)
                .average()
                .orElse(0.0);

        double averageRating = Math.round(average * 10.0) / 10.0;

        Map<Integer, Double> distributionPercentage = distribution.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Math.round((e.getValue() * 100.0 / total) * 10.0) / 10.0
                ));

        return new ReviewSummaryResponseDTO(averageRating, total, distribution, distributionPercentage);
    }
}
