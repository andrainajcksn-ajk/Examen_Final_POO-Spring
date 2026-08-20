package com.example.examFinal_Spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@AllArgsConstructor
@Getter
public class Account {
    private String id;
    private AccountType accountType;
    private List<Transaction> transactions;
}
