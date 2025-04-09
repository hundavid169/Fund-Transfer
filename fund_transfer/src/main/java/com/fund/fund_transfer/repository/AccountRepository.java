package com.fund.fund_transfer.repository;

import com.fund.fund_transfer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository <Account, String> {
    List<Account> findByUserUserUuidOrderByAccountNumberAsc(String userUuid);

    @Query("SELECT MAX(a.accountNumber) FROM Account a")
    Integer findMaxAccountNumber();

    @Query("SELECT SUM(o.amount) FROM Account o WHERE o.user.userUuid = :uuid AND o.currency.code = :currency")
    BigDecimal totalAmountByCurrency(@Param("uuid") String uuid, @Param("currency") String currency);

    default Account getAccountById(String accountNumber) {
        Optional<Account> accountOptional = findById(accountNumber);
        return accountOptional.orElseThrow(() -> new RuntimeException("Account not found"));
    }

    boolean existsByAccountNumber(String accountNumber);
    boolean existsByAccountNumberAndUserUserUuid(String accountNumber, String userUuid);
}
