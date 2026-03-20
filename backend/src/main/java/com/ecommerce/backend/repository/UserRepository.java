package com.ecommerce.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
