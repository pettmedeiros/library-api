package com.peterson.library.api.application.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.peterson.library.api.domain.model.Book;
import com.peterson.library.api.domain.model.BorrowingRecord;
import com.peterson.library.api.domain.model.Member;
import com.peterson.library.api.domain.model.enums.BorrowingStatus;
import com.peterson.library.api.domain.repositories.BookRepository;
import com.peterson.library.api.domain.repositories.BorrowingRecordRepository;
import com.peterson.library.api.domain.repositories.MemberRepository;

@Service
public class BorrowingRecordService {

    private final BookRepository bookRepository;
    private final BorrowingRecordRepository borrowingRepository;
    private final MemberRepository memberRepository;

    public BorrowingRecordService(BookRepository bookRepository,
            BorrowingRecordRepository borrowingRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.borrowingRepository = borrowingRepository;
        this.memberRepository = memberRepository;
    }
    
    public BorrowingRecord borrowBook (UUID bookId, UUID memberID) {
        
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));

        //Verificar se disponível
        if(book.getAvailableCopies() <= 0){
            throw new RuntimeException("Livro indisponível");
        }

        Member member = memberRepository.findById(memberID)
            .orElseThrow(() -> new RuntimeException("Membro não encontrado"));
        
        //Registro de emprestimo
        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setMember(member);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(7));
        record.setStatus(BorrowingStatus.BORROWED);


        //atualizar estoque após emprestimo
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        //salvando no banco de dados
        bookRepository.save(book);
        return borrowingRepository.save(record);
    }

    public BorrowingRecord returnBook (UUID recordId){

        //busca o emprestimo no banco de dados
        BorrowingRecord record = borrowingRepository.findById(recordId)
            .orElseThrow(() -> new RuntimeException("Registro não encontrado"));
        
        if (record.getStatus() == BorrowingStatus.RETURNED) {
            throw new RuntimeException("Livro devolvido!");
        }

        //Captura a data de entrega e atulizar o status para devolvido
        record.setReturnDate(LocalDate.now());
        record.setStatus(BorrowingStatus.RETURNED);

        //Atualizar estoque
        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        //salva no banco de dados 
        bookRepository.save(book);


        return borrowingRepository.save(record);
    }
    
    
}
