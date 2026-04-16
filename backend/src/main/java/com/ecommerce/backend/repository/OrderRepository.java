package com.ecommerce.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query("SELECT o FROM Order o WHERE " +
           "(:dateFrom IS NULL OR o.order_date >= :dateFrom) AND " +
           "(:dateTo IS NULL OR o.order_date <= :dateTo) AND " +
           "(:status IS NULL OR o.status = :status)")
    List<Order> findByFilters(@Param("dateFrom") LocalDateTime dateFrom,
                              @Param("dateTo") LocalDateTime dateTo,
                              @Param("status") OrderStatus status);
}

