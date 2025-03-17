package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Boolean userExists(Account account){
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return true;
        }
        return false;

    }

    public Optional<Account> registerAccount(Account account) {
        // Ensure username is not blank and password is at least 4 characters
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return Optional.empty();
        }

        // Check if username is already taken
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return Optional.empty();
        }

        // Save new account
        return Optional.of(accountRepository.save(account));
    }

    public Optional<Account> login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }
}