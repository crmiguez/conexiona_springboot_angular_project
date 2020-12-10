package com.practicas.conexiona.service;

import com.practicas.conexiona.model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    public List<Account> findAllAccounts();
    public Optional<Account> findAccountById(String accountId);
    public Account addAccount(Account account);
    public Account updateAccount(Account accountDetails, Account account);
    public void deleteAccount (Account account);

}
