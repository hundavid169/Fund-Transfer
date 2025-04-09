package com.fund.fund_transfer.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RedeemGiftResponse {
    private String accountSender;
    private String accountReceiver;
    private BigDecimal amount;
    private String remark;
}
