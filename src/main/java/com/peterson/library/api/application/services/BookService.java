package com.peterson.library.api.application.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.peterson.library.api.application.dto.BookRequestDTO;
import com.peterson.library.api.application.dto.BookResponseDTO;
import com.peterson.library.api.application.mapper.BookMapper;
import com.peterson.library.api.domain.model.Author;
import com.peterson.library.api.domain.model.Book;
import com.peterson.library.api.domain.model.Publisher;
import com.peterson.library.api.domain.model.enums.BorrowingStatus;
import com.peterson.library.api.domain.repositories.AuthorRepository;
import com.peterson.library.api.domain.repositories.BookRepository;
import com.peterson.library.api.domain.repositories.BorrowingRecordRepository;
import com.peterson.library.api.domain.repositories.PublisherRepository;

import jakarta.transaction.Transactional;

    
@Service
public class BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;
    
    public BookService(AuthorRepository authorRepository, BookRepository bookRepository,
            PublisherRepository publisherRepository, BorrowingRecordRepository borrowingRecordRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    @Transactional
    public BookResponseDTO create(BookRequestDTO dto){ //Método para criar o livro 

        //Validar se Autor existe no banco 
        Author author = authorRepository.findById(dto.authorId())
            .orElseThrow(() -> new RuntimeException("Autor não encontrado!"));


        //Validar se Editor existe no banco  
        Publisher publisher = publisherRepository.findById(dto.publisherId())
            .orElseThrow(() -> new RuntimeException("Editor não encontrado!"));

        
        Book book = BookMapper.toEntity(dto, author, publisher);

        Book savedBook = bookRepository.save(book);
        
        return BookMapper.toDTO(savedBook);

    }

     
    // FIND ALL (com paginação e filtro)
    public Page<BookResponseDTO> findAll(Pageable pageable, UUID authorId, UUID publisherId){
        Page<Book> books;

        if (authorId != null){
            books = bookRepository.findByAuthorId(authorId, pageable);
        } else if (publisherId != null){
            books = bookRepository.findByPublisherId(publisherId, pageable);
        } else {
            books = bookRepository.findAll(pageable);
        }

        return books.map(BookMapper::toDTO);  
    }


    public BookResponseDTO findById(UUID id){
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        return BookMapper.toDTO(book);   // ← Retorna DTO
    }

    @Transactional
    public BookResponseDTO update(UUID id, BookRequestDTO dto){

        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        Author author = authorRepository.findById(dto.authorId())
            .orElseThrow(() -> new RuntimeException("Autor não encontrado!"));

        Publisher publisher = publisherRepository.findById(dto.publisherId())
            .orElseThrow(() -> new RuntimeException("Editor não encontrado!"));

        BookMapper.updateEntity(book, dto, author, publisher);

        Book updatedBook = bookRepository.save(book);
        
        return BookMapper.toDTO(updatedBook);   // ← Retorna DTO

    }

    @Transactional
    public BookResponseDTO updateCopies(UUID id, Integer copies){
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (copies < 0 ) {
            throw new RuntimeException("Quantidade inválida");   
        }

        book.setAvailableCopies(copies);
        Book updatedBook = bookRepository.save(book);
        
        return BookMapper.toDTO(updatedBook);   // ← Retorna DTO
    }


    @Transactional
    public void removeBook(UUID id){
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        boolean isBorrowed = borrowingRecordRepository.existsByBookIdAndStatus(
            id, BorrowingStatus.BORROWED
        );

        if (isBorrowed) {
            throw new RuntimeException("Não é possível excluir um livro que está emprestado!");
        }

        bookRepository.delete(book);
    }
}