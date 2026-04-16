package com.ecommerce.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.backend.model.ProductVariant;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    
    @Query("SELECT pv FROM ProductVariant pv WHERE " +
           "(:productId IS NULL OR pv.product.id = :productId) AND " +
           "(:stockMin IS NULL OR pv.stock >= :stockMin) AND " +
           "(:stockMax IS NULL OR pv.stock <= :stockMax) AND " +
           "(:size IS NULL OR :size = '' OR LOWER(pv.size) = LOWER(:size))")
    List<ProductVariant> findByFilters(@Param("productId") Long productId,
                                       @Param("stockMin") Long stockMin,
                                       @Param("stockMax") Long stockMax,
                                       @Param("size") String size);
}

