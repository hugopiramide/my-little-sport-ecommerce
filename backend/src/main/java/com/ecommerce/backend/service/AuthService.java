package com.ecommerce.backend.service;

import java.time.LocalDate;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.dto.auth.*;
import com.ecommerce.backend.dto.auth.RegisterRequest;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.model.enums.Role;
import com.ecommerce.backend.model.vo.Password;
import com.ecommerce.backend.model.vo.PersonalData;
import com.ecommerce.backend.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService( UserRepository userRepository, JwtServiceImpl jwtService, AuthenticationManager authenticationManager ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Objective: Registers a new user, encrypts the password, saves to DB, and returns a JWT.
     *
     * Input: request - DTO containing raw username and password.
     * Output: AuthResponse - The generated JWT token for immediate access.
     */
    // @Transactional: Ensures the save operation is atomic.
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.findByPersonalDataEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        // 1. Build User entity with encoded password and default role
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
        
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    /**
     * Objective: Authenticates credentials using Spring Security and generates a JWT.
     *
     * Input: request - DTO containing username and password.
     * Output: AuthResponse - The generated JWT token if credentials are valid.
     */
    public AuthResponse login(LoginRequest request) {
        // 1. Delegate authentication to Spring Security Manager
        // This will throw AuthenticationException if password/user is wrong
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        
        // 2. Fetch User to generate token (Authenticated at this point)
        User user = userRepository.findByPersonalDataEmail(request.email())
            .orElseThrow(() -> new RuntimeException("User not found despite authentication"));
            
        // 3. Generate Token
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }
}
