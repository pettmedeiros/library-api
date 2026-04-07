package com.peterson.library.api.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.peterson.library.api.application.dto.AuthorRequestDTO;
import com.peterson.library.api.application.dto.AuthorResponseDTO;
import com.peterson.library.api.application.services.AuthorService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authors", description = "Gerenciamento de autores")
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Cadastrar um autor!")
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorRequestDTO dto) { 

        System.out.println("=== DTO RECEBIDO ===");
        System.out.println("Name: " + dto.name());
        System.out.println("Email: " + dto.email());
        System.out.println("Biography: " + dto.biography());
        System.out.println("=====================");

        AuthorResponseDTO response = authorService.createAuthor(dto);

        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Listar todos os autores")
    @GetMapping
    public ResponseEntity<Page<AuthorResponseDTO>> findAll(Pageable pageable) {
        Page<AuthorResponseDTO> authors = authorService.findAll(pageable);
        return ResponseEntity.ok(authors);
    }

    @Operation(summary = "Listar autor por Id")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> findById(@PathVariable UUID id){
        AuthorResponseDTO author = authorService.findById(id);

        return ResponseEntity.ok(author);
    }

    @Operation(summary = "Atualizar autor completamente")
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody AuthorRequestDTO dto){
        
        AuthorResponseDTO updateAuthor = authorService.update(id, dto);

        return ResponseEntity.ok(updateAuthor);
    }

    
    @Operation(summary = "Deletar um autor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){

        authorService.removeAuthor(id);

        return ResponseEntity.noContent().build();
    }
}
