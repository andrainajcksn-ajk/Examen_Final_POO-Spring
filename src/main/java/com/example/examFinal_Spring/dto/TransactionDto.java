package com.example.examFinal_Spring.dto;

import com.example.examFinal_Spring.model.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class TransactionDto {
    private String accountId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String reason;
}