package com.ecommerce.backend.dto.auth;

public record AuthResponse(
    String token,
    UserDTO user,
    boolean requiresVerification,
    long verificationExpiresInSeconds
) {}
