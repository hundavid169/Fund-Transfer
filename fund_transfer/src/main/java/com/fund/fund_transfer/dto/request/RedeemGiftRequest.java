package com.fund.fund_transfer.dto.request;

import com.fund.fund_transfer.validation.constraint.AccountNumberOwnerValid;
import com.fund.fund_transfer.validation.constraint.AccountNumberValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RedeemGiftRequest {
    @NotBlank(message = "Gift Code is required")
    private String giftCode;
    @NotBlank(message = "Account Number is required")
    @AccountNumberOwnerValid
    private String accountNumber;
}
