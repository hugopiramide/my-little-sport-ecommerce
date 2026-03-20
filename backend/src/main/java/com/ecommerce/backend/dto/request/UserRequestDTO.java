package com.ecommerce.backend.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    String name,

    @NotBlank(message = "Surname is required")
    @Size(max = 150, message = "Surname must be at most 150 characters")
    String surname,

    @NotBlank(message = "Username is required")
    @Size(max = 55, message = "Username must be at most 55 characters")
    String username,

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be at most 100 characters")
    String email,

    @NotNull(message = "Birthday is required")
    @Past(message = "Birthday must be in the past")
    LocalDate birthday,

    @Size(max = 255, message = "Profile image URL must be at most 255 characters")
    String profileImgUrl,

    @NotBlank(message = "Password is required")
    String password

) {}
