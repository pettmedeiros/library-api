package com.peterson.library.api.application.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;


public record BorrowingRequestDTO(
    
     @NotNull(message = "Book ID é obrigatório")
    UUID bookId,

    @NotNull(message = "Member ID é obrigatório")
    UUID memberId
    

) {}

    
