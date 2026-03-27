package com.peterson.library.api.application.services;

import org.springframework.stereotype.Service;

import com.peterson.library.api.application.dto.BookDTO;
import com.peterson.library.api.application.dto.BookResponseDTO;
import com.peterson.library.api.application.mapper.BookMapper;
import com.peterson.library.api.domain.model.Author;
import com.peterson.library.api.domain.model.Book;
import com.peterson.library.api.domain.model.Publisher;
import com.peterson.library.api.domain.repositories.AuthorRepository;
import com.peterson.library.api.domain.repositories.BookRepository;
import com.peterson.library.api.domain.repositories.PublisherRepository;

    
@Service
public class BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    
    public BookService(AuthorRepository authorRepository, BookRepository bookRepository,
            PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    public BookResponseDTO create(BookDTO dto){ //Método para criar o livro 

        //Validar se Autor existe no banco 
        Author author = authorRepository.findById(dto.authorId())
            .orElseThrow(() -> new RuntimeException("Autor não encontrado!"));


        //Validar se Editor existe no banco  
        Publisher publisher = publisherRepository.findById(dto.publisherId())
            .orElseThrow(() -> new RuntimeException("Editor não encontrado!"));

        
        Book book = BookMapper.toEntity(dto, author, publisher);

        //Salva em DTO de resposta para retorno da API
        Book saved = bookRepository.save(book);

        //Retornando
        return BookMapper.toDTO(saved);
    }
}
    



