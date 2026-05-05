package com.ecommerce.backend.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.backend.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
        "(:name IS NULL OR :name = '' OR LOWER(p.productData.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
        "(:category IS NULL OR :category = '' OR :category = 'all' OR LOWER(p.category.name) = LOWER(:category))")
    List<Product> findFiltered(@Param("name") String name, @Param("category") String category, Sort sort);

    List<Product> findByProductDataNameContainingIgnoreCaseOrProductDataDescriptionContainingIgnoreCase(String name, String description);

    @Modifying
    @Query("UPDATE Product p SET p.category = NULL WHERE p.category.id = :categoryId")
    void nullifyCategoryByCategoryId(@Param("categoryId") Long categoryId);
}
