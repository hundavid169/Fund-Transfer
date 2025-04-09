package com.fund.fund_transfer.queue;

import com.fund.fund_transfer.model.Account;
import com.fund.fund_transfer.model.Transaction;
import com.fund.fund_transfer.repository.AccountRepository;
import com.fund.fund_transfer.repository.TransactionLogRepository;
import com.fund.fund_transfer.repository.TransactionRepository;
import jakarta.annotation.PostConstruct;
import com.fund.fund_transfer.model.TransactionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.concurrent.Executors;

import static com.fund.fund_transfer.utils.Constant.TRANSACTION_FAILED;
import static com.fund.fund_transfer.utils.Constant.TRANSACTION_SUCCESS;

@Component
@RequiredArgsConstructor
public class TransactionWorker {

    private final TransactionQueue transactionQueue;

    private final TransactionLogRepository transactionLogRepository;

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @PostConstruct
    public void startWorker() {
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                TransactionLog tx = transactionQueue.take();
                process(tx);
            }
        });
    }

    private void process(TransactionLog tx) {
        try {
            Account sender = tx.getAccountSender();
            Account receiver = tx.getAccountReceiver();

            if (sender.getAmount().compareTo(tx.getAmountDeducted()) < 0) {
                tx.setStatus(TRANSACTION_FAILED);
                tx.setDescription("Insufficient balance");
            } else {
                // success
                sender.setAmount(sender.getAmount().subtract(tx.getAmountDeducted()));
                receiver.setAmount(receiver.getAmount().add(tx.getAmountReceived()));
                accountRepository.save(sender);
                accountRepository.save(receiver);

                tx.setStatus(TRANSACTION_SUCCESS);

                Transaction transaction = new Transaction();
                transaction.setTransactionId(tx.getTransactionId());
                transaction.setAccountSender(tx.getAccountSender());
                transaction.setAccountReceiver(tx.getAccountReceiver());
                transaction.setAmountDeducted(tx.getAmountDeducted());
                transaction.setAmountReceived(tx.getAmountReceived());
                transaction.setRemark(tx.getRemark());
                transaction.setTransactionDate(new Date());
                transactionRepository.save(transaction);
            }

            tx.setTransactionDate(new Date());
            transactionLogRepository.save(tx);

        } catch (Exception e) {
            tx.setStatus(TRANSACTION_FAILED);
            tx.setDescription("System error: " + e.getMessage());
            transactionLogRepository.save(tx);
        }
    }
}

