package com.fund.fund_transfer.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GiftResponse {
    private String giftCode;
    private String accountNumber;
    private String currency;
    private BigDecimal amount;
    private String remark;
    private Date expiryDate;
}
