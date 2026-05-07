package com.ecommerce.backend.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record ResendVerificationRequest(
    @NotBlank(message = "Username is required")
    String username
) {}

