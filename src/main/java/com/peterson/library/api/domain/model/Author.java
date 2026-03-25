package com.peterson.library.api.domain.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Author extends Person {

    @Column(columnDefinition = "TEXT")
    private String biography;

    public Author(){

    }

    public Author(UUID id, String name, String email, String biography) {
        super(id, name, email);
        this.biography = biography;
    }

    public Author(String biography) {
        this.biography = biography;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

}
