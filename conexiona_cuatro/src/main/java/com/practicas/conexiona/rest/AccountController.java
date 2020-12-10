package com.practicas.conexiona.rest;

import com.practicas.conexiona.exception.ResourceNotFoundException;
import com.practicas.conexiona.model.Account;
import com.practicas.conexiona.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api/v1")
@RequestMapping("/api/v1/accountmanage")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasAuthority('PERM_READ_ALL_ACCOUNTS')")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountService.findAllAccounts();
    }

    @PreAuthorize("hasAuthority('PERM_READ_ACCOUNT')")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(
            @PathVariable(value = "id") String accountId) throws ResourceNotFoundException {
        Account account = accountService.findAccountById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found on :: "+ accountId));
        return ResponseEntity.ok().body(account);
    }

    @PreAuthorize("hasAuthority('PERM_CREATE_ACCOUNT')")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/account")
    public Account createAccount(@Valid @RequestBody Account account) {
        return accountService.addAccount(account);
    }

    @PreAuthorize("hasAuthority('PERM_UPDATE_ACCOUNT')")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(
            @PathVariable(value = "id") String accountId,
            @Valid @RequestBody Account accountDetails) throws ResourceNotFoundException {
        Account account = accountService.findAccountById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found on :: "+ accountId));

        final Account updatedAccount = accountService.updateAccount(accountDetails, account);
        return ResponseEntity.ok(updatedAccount);
    }

    @PreAuthorize("hasAuthority('PERM_DELETE_ACCOUNT')")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/account/{id}")
    public Map<String, Boolean> deleteAccount(
            @PathVariable(value = "id") String accountId) throws Exception {
        Account account = accountService.findAccountById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found on :: "+ accountId));

        accountService.deleteAccount(account);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
