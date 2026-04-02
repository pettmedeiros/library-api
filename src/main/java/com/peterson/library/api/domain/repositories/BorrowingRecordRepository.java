package com.peterson.library.api.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peterson.library.api.domain.model.BorrowingRecord;
import com.peterson.library.api.domain.model.enums.BorrowingStatus;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, UUID>{

    boolean existsByBookIdAndStatus(UUID bookId, BorrowingStatus status);

}
