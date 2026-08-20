package com.example.examFinal_Spring.service;

import com.example.examFinal_Spring.model.Account;
import com.example.examFinal_Spring.model.Transaction;
import com.example.examFinal_Spring.model.TransactionType;
import com.example.examFinal_Spring.repository.AccountRepository;
import com.example.examFinal_Spring.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final AccountRepository accountRepository = new AccountRepository();

    public List<Transaction> getAll(TransactionType type) {
        return transactionRepository.findAll(type);
    }

    public List<Transaction> getByAccountId(String accountId) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        return transactionRepository.findByAccountId(accountId);
    }

    public Transaction create(String accountId, TransactionType type, BigDecimal amount, String reason) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                Instant.now(),
                type,
                amount,
                reason
        );
        transactionRepository.save(transaction, accountId);
        return transaction;
    }

    public BigDecimal getBalance(String accountId) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        return transactionRepository.getBalanceByAccountId(accountId);
    }
}