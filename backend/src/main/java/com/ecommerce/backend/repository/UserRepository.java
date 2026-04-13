package com.ecommerce.backend.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.backend.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    // Solved Problem with redundant Query
    @EntityGraph(attributePaths = {"cart"})
    Optional<User> findByPersonalDataUsername(String username);

    Optional<User> findByPersonalDataEmail(String email);
}
