package com.peterson.library.api.application.dto;

import java.util.UUID;

public record BookResponseDTO( //saida de dados
    UUID id,
    String title,
    String isbn,
    Integer publishedYear,
    Integer availableCopies,
    String authorName, // id do autor 
    String publisherName  // id da editora
) {

}
