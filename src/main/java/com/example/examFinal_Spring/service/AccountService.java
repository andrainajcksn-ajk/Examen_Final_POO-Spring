package com.example.examFinal_Spring.service;

import com.example.examFinal_Spring.dto.AccountDto;
import com.example.examFinal_Spring.model.Account;
import com.example.examFinal_Spring.repository.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AccountService {
    private final AccountRepository accountRepository = new AccountRepository();
    public AccountDto getById(String id) {
        Account account = accountRepository.findById(id);
        if (account == null) {
            return null;
        }
        return toDto(account);
    }

    public List<AccountDto> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AccountDto toDto(Account account) {
        return new AccountDto(account.getId(), account.getAccountType());
    }
}