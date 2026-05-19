package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.UpdateUserRequestDTO;
import com.ecommerce.backend.model.User;
import java.util.Optional;
import java.util.List;
import java.time.Instant;

public interface UserService {
    Optional<User> findById(Long id);
    User getById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findExpiredNonVerifiedUsers(Instant now);
    User save(User user);
    void delete(User user);
    void deleteAll(List<User> users);
    User updateUserData(Long id, UpdateUserRequestDTO dto);
}
