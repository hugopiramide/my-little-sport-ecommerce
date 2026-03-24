package com.ecommerce.backend.model.vo;

import java.time.LocalDate;
import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class PersonalData {
    
    private static final String REGEX_PATTERN  = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Column(length = 100)
    private String name;

    @Column(length = 150)
    private String surname;

    @Column(name = "user_name", length = 55, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String email;

    @Embedded
    private Birthday birthday;

    @Column(name = "profile_img_url", length = 255)
    private String profileImgUrl;


    public PersonalData(String name, String surname, String username, String email, LocalDate birthday,
            String profileImgUrl) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = (patternMatches(email, REGEX_PATTERN)) ? email : null;
        this.birthday = new Birthday(birthday);
        this.profileImgUrl = profileImgUrl;
    }


    public static boolean patternMatches(String emailAddress, String regexPattern) {
    return Pattern.compile(regexPattern)
        .matcher(emailAddress)
        .matches();
    }
    
}
