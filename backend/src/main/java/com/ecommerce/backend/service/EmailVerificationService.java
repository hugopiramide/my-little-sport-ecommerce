package com.ecommerce.backend.service;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.dto.auth.RegisterResponse;
import com.ecommerce.backend.dto.auth.UserDTO;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;

import java.security.SecureRandom;

@Service
public class EmailVerificationService {

    private static final int CODE_LENGTH = 6;

    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final SecureRandom random = new SecureRandom();

    @Value("${app.mail.from:no-reply@mylittlesport.local}")
    private String mailFrom;

    @Value("${app.security.email-verification.expiration-minutes:15}")
    private long expirationMinutes;

    public EmailVerificationService(JavaMailSender mailSender, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public RegisterResponse createAndSendCode(User user) {
        String rawCode = generateNumericCode();
        Instant now = Instant.now();
        Instant expiresAt = now.plus(Duration.ofMinutes(expirationMinutes));

        user.setEmailVerified(Boolean.FALSE);
        user.setEmailVerificationCodeHash(passwordEncoder.encode(rawCode));
        user.setEmailVerificationCodeExpiry(expiresAt);
        userRepository.save(user);

        System.out.println("[DEV] Email verification code for " + user.getPersonalData().getEmail() + ": " + rawCode);

        sendEmail(user, rawCode);

        long verificationExpiresInSeconds = Duration.between(now, expiresAt).getSeconds();
        UserDTO userDTO = new UserDTO(user, true, verificationExpiresInSeconds);

        return new RegisterResponse(
            true,
            userDTO,
            verificationExpiresInSeconds
        );
    }

    public boolean isCodeValid(User user, String rawCode) {
        if (rawCode == null || rawCode.isBlank()) return false;

        Instant expiresAt = user.getEmailVerificationCodeExpiry();
        if (expiresAt == null || expiresAt.isBefore(Instant.now())) return false;

        String codeHash = user.getEmailVerificationCodeHash();
        if (codeHash == null || codeHash.isBlank()) return false;

        return passwordEncoder.matches(rawCode, codeHash);
    }

    @Transactional
    public void markEmailVerified(User user) {
        user.setEmailVerified(Boolean.TRUE);
        user.setEmailVerificationCodeHash(null);
        user.setEmailVerificationCodeExpiry(null);
        userRepository.save(user);
    }

    private String generateNumericCode() {
        int value = random.nextInt((int) Math.pow(10, CODE_LENGTH));
        return String.format("%0" + CODE_LENGTH + "d", value);
    }

    private void sendEmail(User user, String rawCode) {
        String email = user.getPersonalData().getEmail();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(email);
        message.setSubject("Your verification code - MYLITTLESPORT");
        message.setText(
            "Hello " + user.getPersonalData().getName() + ",\n\n" +
            "Your verification code is: " + rawCode + "\n" +
            "This code expires in approximately " + expirationMinutes + " minutes.\n\n" +
            "Copy and paste the code in the app to activate your account.\n\n" +
            "MYLITTLESPORT"
        );

        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw e;
        }
    }
}

