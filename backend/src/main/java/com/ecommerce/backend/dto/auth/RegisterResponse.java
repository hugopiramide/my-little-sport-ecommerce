package com.ecommerce.backend.dto.auth;

public record RegisterResponse(
    boolean requiresVerification,
    UserDTO user,
    long verificationExpiresInSeconds
) {}
