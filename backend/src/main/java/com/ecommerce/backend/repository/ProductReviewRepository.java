package com.ecommerce.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.backend.model.ProductReview;
import com.ecommerce.backend.model.enums.ReviewStatus;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

       @Query("SELECT r FROM ProductReview r " +
              "JOIN FETCH r.user " +
              "WHERE r.product.id = :productId")
       List<ProductReview> findByProductId(@Param("productId") Long productId);

       @Query("SELECT r FROM ProductReview r " +
              "JOIN FETCH r.user " +
              "WHERE r.product.id = :productId AND r.status = :status")
       List<ProductReview> findByProductIdAndStatus(@Param("productId") Long productId, @Param("status") ReviewStatus status);

       List<ProductReview> findByUserId(Long userId);

       @Query("SELECT r FROM ProductReview r " +
              "JOIN FETCH r.user " +
              "WHERE r.status = :status")
       List<ProductReview> findByStatus(@Param("status") ReviewStatus status);

       Optional<ProductReview> findByOrderId(Long orderId);

       boolean existsByUserIdAndProductIdAndOrderId(Long userId, Long productId, Long orderId);
}
