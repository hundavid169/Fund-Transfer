package com.fund.fund_transfer.service.impl;

import com.fund.fund_transfer.dto.response.TransactionResponse;
import com.fund.fund_transfer.model.Transaction;
import com.fund.fund_transfer.repository.AccountRepository;
import com.fund.fund_transfer.repository.TransactionRepository;
import com.fund.fund_transfer.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.fund.fund_transfer.utils.Constant.TRANSACTION_TYPE_RECEIVED;
import static com.fund.fund_transfer.utils.Constant.TRANSACTION_TYPE_TRANSFERRED;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    @Override
    public List<TransactionResponse> getTransactions(String accountNumber, Principal principal) {
        if(!accountRepository.existsByAccountNumberAndUserUserUuid(accountNumber, principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Number invalid");
        }

        List<Transaction> transactions = transactionRepository.getTransactionsDetail(accountNumber);

        List<TransactionResponse> dtos = transactions.stream()
            .map(transaction -> {
                BigDecimal amount = BigDecimal.ZERO;
                String transactionType = "";
                String currency = "";

                // Apply the conditional logic for sender and receiver
                if (transaction.getAccountSender().getAccountNumber().equals(accountNumber)) {
                    amount = transaction.getAmountDeducted();
                    currency = transaction.getAccountSender().getCurrency().getCode();
                    transactionType = TRANSACTION_TYPE_TRANSFERRED;
                } else if (!ObjectUtils.isEmpty(transaction.getAccountReceiver()) && transaction.getAccountReceiver().getAccountNumber().equals(accountNumber)) {
                    amount = transaction.getAmountReceived();
                    currency = transaction.getAccountReceiver().getCurrency().getCode();
                    transactionType = TRANSACTION_TYPE_RECEIVED;
                }

                return new TransactionResponse(
                        transaction.getTransactionId(),
                        transactionType,
                        transaction.getAccountSender().getAccountNumber(),
                        ObjectUtils.isEmpty(transaction.getAccountReceiver()) ? "" : transaction.getAccountReceiver().getAccountNumber(),
                        amount,
                        currency,
                        transaction.getRemark(),
                        transaction.getTransactionDate()
                );
            })
            .collect(Collectors.toList());
        return dtos;
    }

    public ByteArrayInputStream transactionsToExcel(List<TransactionResponse>  transactions) throws IOException {
        String[] columns = {"Transaction ID", "Transaction Type", "Sender", "Receiver", "Original Amount", "Currency", "Remark", "Transaction Date"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Transactions");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (TransactionResponse transaction : transactions) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(transaction.getTransactionId());
                row.createCell(1).setCellValue(transaction.getTransactionType());
                row.createCell(2).setCellValue(transaction.getAccountSender());
                row.createCell(3).setCellValue(transaction.getAccountReceiver());
                row.createCell(4).setCellValue(transaction.getOriginalAmount().toString());
                row.createCell(5).setCellValue(transaction.getCurrency());
                row.createCell(6).setCellValue(transaction.getRemark());
                row.createCell(7).setCellValue(transaction.getTransactionDate());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
