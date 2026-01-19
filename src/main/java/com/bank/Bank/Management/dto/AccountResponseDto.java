package com.bank.Bank.Management.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountResponseDto {
    private String accountNumber;
    private BigDecimal balance;
    private String status;
}

