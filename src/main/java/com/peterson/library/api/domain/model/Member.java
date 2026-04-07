package com.peterson.library.api.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Member extends Person {

    @Column(nullable = false)
    private LocalDate registrationDate;

    public Member() {

    }

    public Member(UUID id, String name, String email, LocalDate registrationDate) {
        super(id, name, email);
        this.registrationDate = registrationDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    
}
