package com.peterson.library.api.application.mapper;

import com.peterson.library.api.application.dto.BorrowingResponseDTO;
import com.peterson.library.api.domain.model.BorrowingRecord;

public class BorrowingMapper {

    public static BorrowingResponseDTO toDTO(BorrowingRecord record ){
        return new BorrowingResponseDTO(
            record.getId(),

            record.getBook().getId(),
            record.getBook().getTitle(),

            record.getMember().getId(),
            record.getMember().getName(),

            record.getBorrowDate(),
            record.getDueDate(),
            record.getReturnDate(),

            record.getStatus()
        ); 
    }
}

