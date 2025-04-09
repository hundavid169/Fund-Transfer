package com.fund.fund_transfer.dto.request;

import com.fund.fund_transfer.validation.constraint.AccountNumberOwnerValid;
import com.fund.fund_transfer.validation.constraint.AccountNumberValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    @NotBlank(message = "Account Sender is required")
    @AccountNumberOwnerValid
    private String accountSender;
    @NotBlank(message = "Account Receiver is required")
    @AccountNumberValid
    private String accountReceiver;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    private String remark;
}
