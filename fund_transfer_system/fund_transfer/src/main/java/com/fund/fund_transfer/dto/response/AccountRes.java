package com.fund.fund_transfer.dto.response;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class AccountRes {
    private String accountNumber;
    private String accountName;
    private String accountType;
    private String currency;
    private BigDecimal amount;
}
