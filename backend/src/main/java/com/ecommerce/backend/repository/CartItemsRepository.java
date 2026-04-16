package com.ecommerce.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.backend.model.CartItems;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {}

