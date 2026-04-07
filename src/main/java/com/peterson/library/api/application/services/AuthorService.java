package com.peterson.library.api.application.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.peterson.library.api.application.dto.AuthorRequestDTO;
import com.peterson.library.api.application.dto.AuthorResponseDTO;
import com.peterson.library.api.application.mapper.AuthorMapper;
import com.peterson.library.api.domain.model.Author;
import com.peterson.library.api.domain.repositories.AuthorRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorService {

    AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public AuthorResponseDTO createAuthor (AuthorRequestDTO dto){
        Author author = AuthorMapper.toEntity(dto);
        Author savedAuthor = authorRepository.save(author);

        return AuthorMapper.toDTO(savedAuthor);
    }

    public Page<AuthorResponseDTO> findAll (Pageable pageable){
        Page<Author> authors = authorRepository.findAll(pageable);
        return authors.map(AuthorMapper::toDTO);
    }

    
    public AuthorResponseDTO findById(UUID id){
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Autor não econtrado!"));
        
        return AuthorMapper.toDTO(author);
    }
    
    @Transactional
    public AuthorResponseDTO update(UUID id, AuthorRequestDTO dto){
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Autor não encontrado!"));

        Author updatedAuthor = authorRepository.save(author);

        return AuthorMapper.toDTO(updatedAuthor);
    }
    @Transactional
    public void removeAuthor(UUID id){

        Author author = findByIdEntity(id);

        authorRepository.delete(author);

    }

    private Author findByIdEntity(UUID id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado!"));
    }
}
