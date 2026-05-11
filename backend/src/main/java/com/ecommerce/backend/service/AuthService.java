package com.ecommerce.backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.dto.auth.*;
import com.ecommerce.backend.dto.auth.RegisterRequest;
import com.ecommerce.backend.dto.auth.ResendVerificationRequest;
import com.ecommerce.backend.dto.auth.VerifyEmailRequest;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.model.enums.Role;
import com.ecommerce.backend.model.vo.Password;
import com.ecommerce.backend.model.vo.PersonalData;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.interfaces.JwtService;

import java.time.Duration;
import java.time.Instant;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailVerificationService emailVerificationService;

    public AuthService(
        UserRepository userRepository,
        JwtService jwtService,
        AuthenticationManager authenticationManager,
        EmailVerificationService emailVerificationService
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailVerificationService = emailVerificationService;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.findByPersonalDataUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.findByPersonalDataEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        User user = new User(
            new PersonalData(
                request.name(),
                request.surname(),
                request.username(),
                request.email(),
                request.birthday(),
                request.profileImgUrl()),
            new Password(request.password()),
            Role.USER
        );
        
        userRepository.save(user);

        try {
            return emailVerificationService.createAndSendCode(user);
        } catch (Exception e) {
            throw e;
        }
    }

    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        
        User user = (User) authentication.getPrincipal();
            
        String jwtToken = jwtService.generateToken(user);
        
        boolean requiresVerification = !Boolean.TRUE.equals(user.getEmailVerified());
        long verificationExpiresInSeconds = 0;
        if (requiresVerification && user.getEmailVerificationCodeExpiry() != null) {
            verificationExpiresInSeconds = Duration.between(
                Instant.now(),
                user.getEmailVerificationCodeExpiry()
            ).getSeconds();
            if (verificationExpiresInSeconds < 0) {
                verificationExpiresInSeconds = 0;
            }
        }
        
        UserDTO userDTO = new UserDTO(user, requiresVerification, verificationExpiresInSeconds);
        return new AuthResponse(jwtToken, userDTO, requiresVerification, verificationExpiresInSeconds);
    }

    public AuthResponse loginAdmin(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = (User) authentication.getPrincipal();
        if (user.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Only ADMIN users can log in");
        }

        String jwtToken = jwtService.generateToken(user);
        
        boolean requiresVerification = !Boolean.TRUE.equals(user.getEmailVerified());
        long verificationExpiresInSeconds = 0;
        if (requiresVerification && user.getEmailVerificationCodeExpiry() != null) {
            verificationExpiresInSeconds = Duration.between(
                Instant.now(),
                user.getEmailVerificationCodeExpiry()
            ).getSeconds();
            if (verificationExpiresInSeconds < 0) {
                verificationExpiresInSeconds = 0;
            }
        }
        
        UserDTO userDTO = new UserDTO(user, requiresVerification, verificationExpiresInSeconds);
        return new AuthResponse(jwtToken, userDTO, requiresVerification, verificationExpiresInSeconds);
    }

    @Transactional
    public AuthResponse verifyEmail(VerifyEmailRequest request) {
        User user = userRepository.findByPersonalDataUsername(request.username())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (Boolean.TRUE.equals(user.getEmailVerified())) {
            String jwtToken = jwtService.generateToken(user);
            UserDTO userDTO = new UserDTO(user, false, 0);
            return new AuthResponse(jwtToken, userDTO, false, 0);
        }

        boolean isValid = emailVerificationService.isCodeValid(user, request.code());
        if (!isValid) {
            Instant expiresAt = user.getEmailVerificationCodeExpiry();
            if (expiresAt != null && expiresAt.isBefore(Instant.now())) {
                userRepository.delete(user);
            }
            throw new IllegalArgumentException("Invalid or expired verification code");
        }

        emailVerificationService.markEmailVerified(user);
        String jwtToken = jwtService.generateToken(user);
        UserDTO userDTO = new UserDTO(user, false, 0);
        return new AuthResponse(jwtToken, userDTO, false, 0);
    }

    @Transactional
    public RegisterResponse resendVerificationCode(ResendVerificationRequest request) {
        User user = userRepository.findByPersonalDataUsername(request.username())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (Boolean.TRUE.equals(user.getEmailVerified())) {
            UserDTO userDTO = new UserDTO(user, false, 0);
            return new RegisterResponse(false, userDTO, 0);
        }

        return emailVerificationService.createAndSendCode(user);
    }
}
