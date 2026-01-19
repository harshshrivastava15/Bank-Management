package com.bank.Bank.Management.service;

import com.bank.Bank.Management.entity.Account;
import com.bank.Bank.Management.entity.Transaction;
import com.bank.Bank.Management.entity.TransactionType;
import com.bank.Bank.Management.repository.AccountRepository;
import com.bank.Bank.Management.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void transfer(String fromAcc, String toAcc, BigDecimal amount) {

        Account from = accountRepository.findByAccountNumber(fromAcc).orElseThrow();
        Account to = accountRepository.findByAccountNumber(toAcc).orElseThrow();

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        Transaction txn = new Transaction();
        txn.setAmount(amount);
        txn.setType(TransactionType.TRANSFER);
        txn.setTimestamp(LocalDateTime.now());
        txn.setFromAccount(from);
        txn.setToAccount(to);

        accountRepository.save(from);
        accountRepository.save(to);
        transactionRepository.save(txn);
    }
}
