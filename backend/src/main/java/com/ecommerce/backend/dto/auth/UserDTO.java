package com.ecommerce.backend.dto.auth;

import com.ecommerce.backend.model.enums.Role;

public record UserDTO(
    Long id,
    String username,
    Boolean emailVerified,
    String email,
    Role role,
    Boolean requiresVerification,
    long verificationExpiresInSeconds
) {}