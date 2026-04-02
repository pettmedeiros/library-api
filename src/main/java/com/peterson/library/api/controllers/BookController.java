package com.peterson.library.api.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.peterson.library.api.application.dto.BookRequestDTO;
import com.peterson.library.api.application.dto.BookResponseDTO;
import com.peterson.library.api.application.mapper.BookMapper;
import com.peterson.library.api.application.services.BookService;
import com.peterson.library.api.domain.model.Book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Books", description = "Gerenciamento de livros")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @Operation(summary = "Cadastrar um novo livro")
    @PostMapping
    public ResponseEntity<BookResponseDTO> create (@Valid @RequestBody BookRequestDTO dto){

        Book book = bookService.create(dto);

        return ResponseEntity.status(201).body(BookMapper.toDTO(book));
    }

    @Operation(summary = "Listar livros com paginação e filtro")
    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> findAll(
            @RequestParam(required = false) UUID authorId,
            @RequestParam(required = false) UUID publisherId,
            Pageable pageable
    ){

        Page<Book> books = bookService.findAll(pageable, authorId, publisherId);

        return ResponseEntity.ok(
            books.map(BookMapper::toDTO)
        );

    } 

    @Operation(summary = "Listar livro por Id")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> findById (@PathVariable UUID id){
        Book book = bookService.findById(id);

        return ResponseEntity.ok(BookMapper.toDTO(book));
    }

    @Operation(summary = "Atualizar completamente um livro")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update (
            @PathVariable UUID id,
            @Valid @RequestBody BookRequestDTO dto){
        
        Book book = bookService.update(id, dto);
        
        return ResponseEntity.ok(BookMapper.toDTO(book));

    }

    @Operation(summary = "Atualizar parcialmente um livro")
    @PatchMapping("/{id}/copies")
    public ResponseEntity<BookResponseDTO> updateCopies(
            @PathVariable UUID id,
            @RequestParam int copies){

        Book book = bookService.updateCopies(id, copies);
        
        return ResponseEntity.ok(BookMapper.toDTO(book));
    }


    @Operation(summary = "Remover um livro")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable UUID id){
        bookService.removeBook(id);
    
        return ResponseEntity.noContent().build();
    }


}
