package com.ecommerce.backend.model.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Period;

@Embeddable
@NoArgsConstructor
@Getter
@ToString
public class Birthday {

    private LocalDate date;

    public Birthday(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Birthday cannot be null");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birthday cannot be in the future");
        }
        if (date.isBefore(LocalDate.now().minusYears(120))) {
            throw new IllegalArgumentException("Invalid birth date: age cannot exceed 120 years");
        }
        this.date = date;
    }

    public int getAge() {
        return Period.between(date, LocalDate.now()).getYears();
    }

    public boolean isAdult() {
        return getAge() >= 18;
    }

    public int getBirthYear() {
        return date.getYear();
    }

}
