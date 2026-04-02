package com.peterson.library.api.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.peterson.library.api.application.dto.BookRequestDTO;
import com.peterson.library.api.application.mapper.BookMapper;
import com.peterson.library.api.domain.model.Author;
import com.peterson.library.api.domain.model.Book;
import com.peterson.library.api.domain.model.Publisher;
import com.peterson.library.api.domain.model.enums.BorrowingStatus;
import com.peterson.library.api.domain.repositories.AuthorRepository;
import com.peterson.library.api.domain.repositories.BookRepository;
import com.peterson.library.api.domain.repositories.BorrowingRecordRepository;
import com.peterson.library.api.domain.repositories.PublisherRepository;

    
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

    public Book create(BookRequestDTO dto){ //Método para criar o livro 

        //Validar se Autor existe no banco 
        Author author = authorRepository.findById(dto.authorId())
            .orElseThrow(() -> new RuntimeException("Autor não encontrado!"));


        //Validar se Editor existe no banco  
        Publisher publisher = publisherRepository.findById(dto.publisherId())
            .orElseThrow(() -> new RuntimeException("Editor não encontrado!"));

        
        Book book = BookMapper.toEntity(dto, author, publisher);

        //Salva em DTO de resposta para retorno da API
        return bookRepository.save(book);

    }

        // FIND ALL (com paginação e filtro)
    public Page<Book> findAll(Pageable pageable, UUID authorId, UUID publisherId){
        if (authorId != null){
            return bookRepository.findByAuthorId(authorId, pageable);
        }

        if (publisherId != null){
            return bookRepository.findByPublisherId(publisherId, pageable);
        }

        return bookRepository.findAll(pageable);
    }

    public Book findById(UUID id){
        return bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

    }

    public Book update(UUID id, BookRequestDTO dto){

        Book book = findById(id);

        Author author = authorRepository.findById(dto.authorId())
            .orElseThrow(() -> new RuntimeException("Autor não encontrado!"));

        Publisher publisher = publisherRepository.findById(dto.publisherId())
            .orElseThrow(() -> new RuntimeException("Editor não encontrado!"));

        BookMapper.updateEntity(book, dto, author, publisher);

        return bookRepository.save(book);
    }

    public Book updateCopies(UUID id, Integer copies){

        Book book = findById(id);

        if (copies < 0 ) {
            throw new RuntimeException("Quantidade inválida");   
        }

        book.setAvailableCopies(copies);
        return bookRepository.save(book);
    }


    public void removeBook(UUID id){
        Book book = findById(id);

        // 🔥 REGRA: não pode deletar se estiver emprestado
        boolean isBorrowed = borrowingRecordRepository.existsByBookIdAndStatus(
            id, BorrowingStatus.BORROWED
        );

        if (isBorrowed) {
            throw new RuntimeException("Não é possível excluir um livro que está emprestado!");
        }

        bookRepository.delete(book);
    }
}

