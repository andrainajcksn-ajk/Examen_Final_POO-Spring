package com.example.examFinal_Spring.controller;

import com.example.examFinal_Spring.dto.TransactionDto;
import com.example.examFinal_Spring.model.Transaction;
import com.example.examFinal_Spring.model.TransactionType;
import com.example.examFinal_Spring.service.TransactionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    private final TransactionService transactionService = new TransactionService();

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAll(
            @RequestParam(required = false) String type) {

        TransactionType transactionType = null;
        if (type != null) {
            transactionType = TransactionType.valueOf(type.toUpperCase());
        }

        List<Transaction> transactions = transactionService.getAll(transactionType);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/accounts/{id}/transactions")
    public ResponseEntity<List<Transaction>> getByAccountId(@PathVariable String id) {
        try {
            List<Transaction> transactions = transactionService.getByAccountId(id);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> create(@RequestBody TransactionDto request) {
        try {
            Transaction transaction = transactionService.create(
                    request.getAccountId(),
                    request.getTransactionType(),
                    request.getAmount(),
                    request.getReason()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}