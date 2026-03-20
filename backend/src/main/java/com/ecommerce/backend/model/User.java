package com.ecommerce.backend.model;

import com.ecommerce.backend.model.enums.Role;
import com.ecommerce.backend.model.vo.Password;
import com.ecommerce.backend.model.vo.PersonalData;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PersonalData personalData;

    @Embedded
    private Password password;

    @Enumerated(EnumType.STRING)
    private Role role;
    
}