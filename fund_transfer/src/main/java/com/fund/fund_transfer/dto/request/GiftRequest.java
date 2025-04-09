package com.fund.fund_transfer.dto.request;

import com.fund.fund_transfer.validation.constraint.AccountNumberOwnerValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GiftRequest {
    @NotBlank(message = "Amount Number is required")
    @AccountNumberOwnerValid
    private String accountNumber;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    private String remark;
}
