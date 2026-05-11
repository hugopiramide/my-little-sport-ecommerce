package com.ecommerce.backend.dto.auth;

import com.ecommerce.backend.model.User;
import java.time.LocalDate;

public record UserDTO(
    String username,
    String name,
    String surname,
    String profileImgUrl,
    LocalDate birthday,
    Boolean emailVerified,
    String email,
    Boolean requiresVerification,
    long verificationExpiresInSeconds
) {
    public UserDTO(User user, Boolean requiresVerification, long verificationExpiresInSeconds) {
        this(
            user.getUsername(),
            user.getPersonalData().getName(),
            user.getPersonalData().getSurname(),
            user.getPersonalData().getProfileImgUrl(),
            user.getPersonalData().getBirthday() != null ? user.getPersonalData().getBirthday().getDate() : null,
            user.getEmailVerified(),
            user.getPersonalData().getEmail(),
            requiresVerification,
            verificationExpiresInSeconds
        );
    }
}