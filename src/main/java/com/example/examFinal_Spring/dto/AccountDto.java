package com.example.examFinal_Spring.dto;

import com.example.examFinal_Spring.model.AccountType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountDto {
    private String id;
    private AccountType accountType;
}
