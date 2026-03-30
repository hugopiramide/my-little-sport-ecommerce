package com.ecommerce.backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByPersonalDataUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
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

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        
        User user = (User) authentication.getPrincipal();
            
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }
}
