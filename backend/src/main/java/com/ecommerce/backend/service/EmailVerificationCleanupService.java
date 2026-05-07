package com.ecommerce.backend.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;

@Service
public class EmailVerificationCleanupService {

    private final UserRepository userRepository;

    @Value("${app.security.email-verification.cleanup-interval-ms:60000}")
    private long cleanupIntervalMs;

    public EmailVerificationCleanupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(fixedDelayString = "${app.security.email-verification.cleanup-interval-ms:60000}")
    public void cleanupExpiredVerificationCodes() {
        Instant now = Instant.now();
        List<User> expiredUsers = userRepository.findByEmailVerifiedFalseAndEmailVerificationCodeExpiryBefore(now);
        if (expiredUsers.isEmpty()) return;

        userRepository.deleteAll(expiredUsers);
    }
}

