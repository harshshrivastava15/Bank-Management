package com.bank.Bank.Management.controller;

import com.bank.Bank.Management.dto.AccountResponseDto;
import com.bank.Bank.Management.dto.UserResponseDto;
import com.bank.Bank.Management.entity.User;
import com.bank.Bank.Management.repository.TransactionRepository;
import com.bank.Bank.Management.repository.UserRepository;
import com.bank.Bank.Management.service.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public UserController(UserRepository userRepository,
                          TransactionRepository transactionRepository,
                          AccountService accountService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @GetMapping("/me")
    public UserResponseDto getMyProfile(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @GetMapping("/account")
    public AccountResponseDto getMyAccount(Authentication authentication) {
        var account = userRepository.findByEmail(authentication.getName())
                .orElseThrow()
                .getAccount();

        AccountResponseDto dto = new AccountResponseDto();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setStatus(account.getStatus().name());
        return dto;
    }


    @GetMapping("/transactions")
    public Object getMyTransactions(Authentication authentication) {
        Long accountId = userRepository.findByEmail(authentication.getName())
                .orElseThrow()
                .getAccount()
                .getId();

        return transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
    }


    @PostMapping("/transfer")
    public String transfer(@RequestParam String fromAccount,
                           @RequestParam String toAccount,
                           @RequestParam BigDecimal amount) {

        accountService.transfer(fromAccount, toAccount, amount);
        return "Transfer successful";
    }
}

