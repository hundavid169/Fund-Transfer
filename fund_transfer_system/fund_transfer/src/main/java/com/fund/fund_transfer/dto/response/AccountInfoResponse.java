package com.fund.fund_transfer.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountInfoResponse {
    private BigDecimal totalAmountUSD;
    private BigDecimal totalAmountKHR;
    private List<AccountRes> accounts;
}
