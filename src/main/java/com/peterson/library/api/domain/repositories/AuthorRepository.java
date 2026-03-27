package com.peterson.library.api.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peterson.library.api.domain.model.Author;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

}
