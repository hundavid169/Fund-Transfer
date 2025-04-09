package com.fund.fund_transfer.service.impl;

import com.fund.fund_transfer.dto.request.TransferRequest;
import com.fund.fund_transfer.model.Account;
import com.fund.fund_transfer.model.TransactionLog;
import com.fund.fund_transfer.queue.TransactionQueue;
import com.fund.fund_transfer.repository.AccountRepository;
import com.fund.fund_transfer.repository.ExchangeRateRepository;
import com.fund.fund_transfer.repository.TransactionLogRepository;
import com.fund.fund_transfer.service.TransferService;
import com.fund.fund_transfer.utils.HelperUtil;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static com.fund.fund_transfer.utils.Constant.TRANSACTION_PENDING;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private static final Logger log = LogManager.getLogger(TransferServiceImpl.class);
    private final AccountRepository accountRepository;

    private final ExchangeRateRepository exchangeRateRepository;
    private final TransactionLogRepository transactionLogRepository;
    private final TransactionQueue transactionQueue;

    private final HelperUtil helperUtil;
    @Override
    public TransferRequest transferMoney(TransferRequest req) throws Exception {
        log.info("transferMoney");
        try {
            Account sender = accountRepository.getAccountById(req.getAccountSender());
            Account receiver = accountRepository.getAccountById(req.getAccountReceiver());

            BigDecimal deductAmount;
            if(sender.getCurrency().getCode().equals(receiver.getCurrency().getCode())){
                // the same currency
                deductAmount = req.getAmount();
            } else {
                // cross currency
                BigDecimal rate = exchangeRateRepository.getExchangeRate(receiver.getCurrency().getCode(), sender.getCurrency().getCode());
                deductAmount = HelperUtil.convertCurrency(req.getAmount(), rate);
            }

            if (sender.getAmount().compareTo(deductAmount) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
            }

            TransactionLog transactionLog = new TransactionLog();
            transactionLog.setTransactionId(helperUtil.generateTransactionId());
            transactionLog.setAccountSender(getAccountById(req.getAccountSender()));
            transactionLog.setAccountReceiver(getAccountById(req.getAccountReceiver()));
            transactionLog.setAmountDeducted(deductAmount);
            transactionLog.setAmountReceived(req.getAmount());
            transactionLog.setRemark(req.getRemark());
            transactionLog.setStatus(TRANSACTION_PENDING);
            transactionLog.setTransactionDate(new Date());
            transactionLogRepository.save(transactionLog);
            // submit queue
            transactionQueue.submit(transactionLog);
        } catch (Exception ex) {
            log.error("transferMoney", ex);
            throw ex;
        }
        return req;
    }

    private Account getAccountById(String accountNumber) {
        Account account = null;
        try {
            Optional<Account> accountOptional = accountRepository.findById(accountNumber);
            account = accountOptional.orElseThrow(() -> new RuntimeException("Account not found"));
        } catch (Exception ex) {
            log.error("getAccountById", ex);
        }
        return account;
    }
}
