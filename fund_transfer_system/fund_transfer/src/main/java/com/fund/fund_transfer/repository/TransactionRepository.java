package com.fund.fund_transfer.repository;

import com.fund.fund_transfer.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT o FROM Transaction o WHERE o.accountSender.accountNumber = :accountNumber OR o.accountReceiver.accountNumber = :accountNumber")
    List<Transaction> getTransactionsDetail(@Param("accountNumber") String accountNumber);
}
