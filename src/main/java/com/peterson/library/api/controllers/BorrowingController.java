package com.peterson.library.api.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peterson.library.api.application.dto.BorrowingRequestDTO;
import com.peterson.library.api.application.dto.BorrowingResponseDTO;
import com.peterson.library.api.application.mapper.BorrowingMapper;
import com.peterson.library.api.application.services.BorrowingRecordService;
import com.peterson.library.api.domain.model.BorrowingRecord;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@Tag(name = "Borrowings", description = "Gerenciamento de empréstimos de livros")
@RestController
@RequestMapping("api/borrowings")
public class BorrowingController {

    private final BorrowingRecordService borrowingRecordService;

    public BorrowingController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    //Emprestimo do livro
    @Operation(summary = "Realizar empréstimo de livro")
    @PostMapping
    public ResponseEntity<BorrowingResponseDTO> borrow (@Valid @RequestBody BorrowingRequestDTO dto){

        BorrowingRecord record = borrowingRecordService.borrowBook(
            dto.bookId(),
            dto.memberId()
        );
        return ResponseEntity
            .status(201) // criado
            .body(BorrowingMapper.toDTO(record));

    }

    //devolver livro
    @Operation(summary = "Realizar empréstimo de livro")
    @PutMapping("/{id}/return")
    public ResponseEntity<BorrowingResponseDTO> returnBook (@PathVariable UUID id){

        BorrowingRecord record = borrowingRecordService.returnBook(id);

        return ResponseEntity.ok(BorrowingMapper.toDTO(record));
    }

    //Listar todos empréstimos
    @Operation(summary = "Devolver livro")
    @GetMapping
    public ResponseEntity<List<BorrowingResponseDTO>> findAll(){
        List<BorrowingRecord> records = borrowingRecordService.findAll();

        List<BorrowingResponseDTO> response = records.stream().map(BorrowingMapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(response);

    }

    //listar empréstimo por Id
    @GetMapping("/{id}")
    public ResponseEntity<BorrowingResponseDTO> findById(@PathVariable UUID id) {

        BorrowingRecord record = borrowingRecordService.findById(id);

        return ResponseEntity.ok(BorrowingMapper.toDTO(record));
    }
}



