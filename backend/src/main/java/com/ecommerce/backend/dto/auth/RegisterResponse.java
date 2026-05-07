package com.ecommerce.backend.dto.auth;

public record RegisterResponse(
    boolean requiresVerification,
    String username,
    String email,
    long verificationExpiresInSeconds
) {}

