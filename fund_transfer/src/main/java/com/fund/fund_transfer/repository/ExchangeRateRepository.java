package com.fund.fund_transfer.repository;

import com.fund.fund_transfer.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    @Query("SELECT o.rate FROM ExchangeRate o WHERE o.currencyFrom = :from AND o.currencyTo = :to")
    BigDecimal getExchangeRate(@Param("from") String from, @Param("to") String to);
}
