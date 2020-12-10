package com.practicas.conexiona.service;

import com.practicas.conexiona.model.Account;
import com.practicas.conexiona.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> findAllAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    @Override
    public Optional<Account> findAccountById(String accountId){
        return accountRepository.findById(accountId);
    }

    @Override
    public Account addAccount(Account account){
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account accountDetails, Account account){
        account.setEmailAddress(accountDetails.getEmailAddress());
        account.setAccountName(accountDetails.getAccountName());
        account.setCompany(accountDetails.getCompany());
        account.setDicomId(accountDetails.getDicomId());
        account.setAddress(accountDetails.getAddress());
        account.setEnabled(accountDetails.getEnabled());

        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount (Account account){
        accountRepository.delete(account);
    }

}
