package com.ecommerce.backend.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.service.interfaces.UserService;

@Service
public class EmailVerificationCleanupService {

    private final UserService userService;

    @Value("${app.security.email-verification.cleanup-interval-ms:60000}")
    private long cleanupIntervalMs;

    public EmailVerificationCleanupService(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedDelayString = "${app.security.email-verification.cleanup-interval-ms:60000}")
    public void cleanupExpiredVerificationCodes() {
        Instant now = Instant.now();
        List<User> expiredUsers = userService.findExpiredNonVerifiedUsers(now);
        if (expiredUsers.isEmpty()) return;

        userService.deleteAll(expiredUsers);
    }
}

