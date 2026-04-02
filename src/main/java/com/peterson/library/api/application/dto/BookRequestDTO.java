package com.peterson.library.api.application.dto;

import java.util.UUID;

public record BookRequestDTO( //entrada de dados 
    
    String title,
    String isbn,
    Integer publishedYear,
    Integer availableCopies,
    UUID authorId, // id do autor 
    UUID publisherId  //id da editora
) {}

/*
classe especial do Java usada para representar dados de forma simples
 * 
 * ✔ substitui uma class com:
 * - atributos
 * - construtor
 * - getters
 * - equals/hashCode
 * - toString
 *  */