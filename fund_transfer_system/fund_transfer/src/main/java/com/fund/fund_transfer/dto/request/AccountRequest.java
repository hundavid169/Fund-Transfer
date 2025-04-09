package com.fund.fund_transfer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {
    @NotBlank(message = "Account Name is required")
    private String accountName;
    @NotBlank(message = "Account Type is required")
    private String accountType;
    @NotBlank(message = "Currency is required")
    private String currency;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
}
