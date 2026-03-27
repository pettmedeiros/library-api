package com.peterson.library.api.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peterson.library.api.domain.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, UUID> {

}
