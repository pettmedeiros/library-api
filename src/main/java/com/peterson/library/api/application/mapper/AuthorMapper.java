package com.peterson.library.api.application.mapper;

import com.peterson.library.api.application.dto.AuthorRequestDTO;
import com.peterson.library.api.application.dto.AuthorResponseDTO;
import com.peterson.library.api.domain.model.Author;

public class AuthorMapper {

    public static Author toEntity(AuthorRequestDTO dto){
        Author author = new Author();
        author.setName(dto.name());
        author.setEmail(dto.email());
        author.setBiography(dto.biography());

        return author;
    }

    public static AuthorResponseDTO toDTO (Author author){
        return new AuthorResponseDTO(
            author.getId(),
            author.getName(),
            author.getEmail(),
            author.getBiography()
        );   
    }

    // Método para update (PUT) para atualizar autor
    public static void updateEntity(Author author, AuthorRequestDTO dto) {
        author.setName(dto.name());
        author.setEmail(dto.email());
        author.setBiography(dto.biography());
    }
}
