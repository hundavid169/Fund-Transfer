package com.fund.fund_transfer.repository;

import com.fund.fund_transfer.model.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
    @Query("SELECT COUNT(t) FROM TransactionLog t WHERE FUNCTION('DATE', t.createDate) = CURRENT_DATE")
    long countTodayTransactions();
}
