package com.ecommerce.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE OrderItem oi SET oi.productVariant = NULL WHERE oi.productVariant.id = :variantId")
    int clearProductVariantReference(@Param("variantId") Long variantId);

    @Modifying
    @Transactional
    @Query("UPDATE OrderItem oi SET oi.productVariant = NULL WHERE oi.productVariant.id IN :variantIds")
    int clearProductVariantReferences(@Param("variantIds") List<Long> variantIds);

    @Query("SELECT oi FROM OrderItem oi WHERE (:orderId IS NULL OR oi.order.id = :orderId) AND (:productName IS NULL OR LOWER(oi.productName) LIKE LOWER(CONCAT('%', :productName, '%')))")
    List<OrderItem> findByFilters(@Param("orderId") Long orderId, @Param("productName") String productName);
}

