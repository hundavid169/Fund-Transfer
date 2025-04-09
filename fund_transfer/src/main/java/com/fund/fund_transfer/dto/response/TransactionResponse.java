package com.fund.fund_transfer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private String transactionId;
    private String transactionType;
    private String accountSender;
    private String accountReceiver;
    private BigDecimal originalAmount;
    private String currency;
    private String remark;
    private Date transactionDate;
}
