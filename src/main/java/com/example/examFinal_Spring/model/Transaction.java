package com.example.examFinal_Spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@Getter
public class Transaction {
    private String id;
    private Instant createdAt;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String reason;
}
