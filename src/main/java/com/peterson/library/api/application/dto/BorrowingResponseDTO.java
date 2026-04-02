package com.peterson.library.api.application.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.peterson.library.api.domain.model.enums.BorrowingStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de um empréstimo")
public record BorrowingResponseDTO(

    @Schema(description = "Resposta de um empréstimo")
    UUID id,

    // Book
    @Schema(description = "ID do empréstimo")
    UUID bookId,
    @Schema(description = "ID do livro")
    String bookTitle,

    // Member
    @Schema(description = "Id do livro")
    UUID memberId,
    @Schema(description = "Nome do membro")
    String memberName,

    // Dates
    @Schema(description = "Data de empréstimo")
    LocalDate borrowDate,
    @Schema(description = "Data prevista de devolução")
    LocalDate dueDate,
    @Schema(description = "Data de devolução")
    LocalDate returnDate,

    // Status
    @Schema(description = "Status do empréstimo")
    BorrowingStatus status

) {}

