package com.example.service;

import com.example.controller.*;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;




@Service
public class AccountService {


    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public Account login(Account account) throws RuntimeException {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword())
                .orElse(null);
    }


    public int register(Account account) throws RuntimeException {
        Account accountCheck = accountRepository.findByUsername(account.getUsername())
                .orElse(null);
        
        if(account.getPassword().length() == 0 || account.getUsername().length() < 1) {
            return 400;
        }
        
        
        
        if(accountCheck == null) {
            accountRepository.save(account);
            return 200;
        }


        

        return 409;
    }









}
