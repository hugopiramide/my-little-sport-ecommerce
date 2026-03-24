package com.ecommerce.backend.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class Password {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private static final int MIN_LENGTH = 8;
    private static final Pattern STRONG_PASSWORD_PATTERN = Pattern.compile(
        "^(?=\\S+$)" +               // no whitespace
        "(?=.*[0-9])" +              // min 1 digit
        "(?=.*[a-z])" +              // min 1 lowercase
        "(?=.*[A-Z])" +              // min 1 Uppercase character
        "(?=.*[^A-Za-z0-9])" +       // min 1 special character
        ".{" + MIN_LENGTH + ",}$"    // min long
    );

    @Column(name = "password", length = 255, nullable = false)
    private String hashedPassword;

    public Password(String rawPassword) {
        Objects.requireNonNull(rawPassword, "Password cannot be null");

        String trimmedPassword = rawPassword.trim();

        if (trimmedPassword.isEmpty()) {
            //TODO: IllegalArtgumentExcepton: Password cannot be emoty
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (!STRONG_PASSWORD_PATTERN.matcher(trimmedPassword).matches()) {
            // TODO: IllegalArgumentException: Not matchs with patterns required
            throw new IllegalArgumentException(
                "Password must be at least " + MIN_LENGTH + " characters and include uppercase, " +
                "lowercase, digits and special characters"
            );
        }

        this.hashedPassword = encoder.encode(trimmedPassword);
    }

    public boolean matches(String rawPassword) {
        Objects.requireNonNull(rawPassword, "Password to check cannot be null");
        return encoder.matches(rawPassword, this.hashedPassword);
    }

}
