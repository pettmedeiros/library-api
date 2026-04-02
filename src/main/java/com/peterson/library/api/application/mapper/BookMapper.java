package com.peterson.library.api.application.mapper;


import com.peterson.library.api.application.dto.BookRequestDTO;
import com.peterson.library.api.application.dto.BookResponseDTO;
import com.peterson.library.api.domain.model.Author;
import com.peterson.library.api.domain.model.Book;
import com.peterson.library.api.domain.model.Publisher;

public class BookMapper {

    public static Book toEntity(BookRequestDTO dto, Author author, Publisher publisher) {

        Book book = new Book(); 
        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());
        book.setPublishedYear(dto.publishedYear());
        book.setAvailableCopies(dto.availableCopies());
        book.setAuthor(author);
        book.setPublisher(publisher); 

        return book;

    }

    public static BookResponseDTO toDTO(Book book){

        return new BookResponseDTO(
            book.getId(),
            book.getTitle(),
            book.getIsbn(),
            book.getPublishedYear(),
            book.getAvailableCopies(),
            book.getAuthor().getName(),
            book.getPublisher().getName()
        );
    }

    public static void updateEntity(Book book, BookRequestDTO dto, Author author, Publisher publisher) {
        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());
        book.setPublishedYear(dto.publishedYear());
        book.setAvailableCopies(dto.availableCopies());
        book.setAuthor(author);
        book.setPublisher(publisher);
    }
}

