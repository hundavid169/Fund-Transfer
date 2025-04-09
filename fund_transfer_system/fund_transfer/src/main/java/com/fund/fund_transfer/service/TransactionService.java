package com.fund.fund_transfer.service;

import com.fund.fund_transfer.dto.response.TransactionResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
public interface TransactionService {
    List<TransactionResponse> getTransactions(String accountNumber, Principal principal);

    ByteArrayInputStream transactionsToExcel(List<TransactionResponse> transactions) throws IOException;
}
