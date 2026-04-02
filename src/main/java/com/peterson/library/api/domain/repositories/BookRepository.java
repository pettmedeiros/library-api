package com.peterson.library.api.domain.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.peterson.library.api.domain.model.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {

    Page<Book> findByAuthorId(UUID authorId, Pageable pageable);

    Page<Book> findByPublisherId(UUID publisherId, Pageable pageable);

}
