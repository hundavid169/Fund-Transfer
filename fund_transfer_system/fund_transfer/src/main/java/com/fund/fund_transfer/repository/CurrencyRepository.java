package com.fund.fund_transfer.repository;

import com.fund.fund_transfer.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findCurrencyByCode(String code);
}
