package com.ecommerce.backend.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequestDTO(
    @NotBlank(message = "Name cannot be blank")
    String name,

    @NotBlank(message = "Surname cannot be blank")
    String surname,

    String profileImgUrl,

    LocalDate birthday,

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    String email
) {}
