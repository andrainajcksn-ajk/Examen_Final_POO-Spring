package com.example.examFinal_Spring.controller;

import com.example.examFinal_Spring.service.TransactionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountController {
    private final TransactionService transactionService = new TransactionService();

    @GetMapping("/account/{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String id) {
        try {
            BigDecimal balance = transactionService.getBalance(id);
            return ResponseEntity.ok(balance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}