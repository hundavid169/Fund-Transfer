package com.fund.fund_transfer.seed;

import com.fund.fund_transfer.model.Currency;
import com.fund.fund_transfer.model.ExchangeRate;
import com.fund.fund_transfer.repository.CurrencyRepository;
import com.fund.fund_transfer.repository.ExchangeRateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    public void run(String... args) throws Exception {
        if (currencyRepository.count() == 0) {
            currencyRepository.save(new Currency("USD", "United States Dollar"));
            currencyRepository.save(new Currency("KHR", "Khmer Riel"));
        }

        if (exchangeRateRepository.count() == 0) {
            exchangeRateRepository.save(new ExchangeRate("USD", "KHR", BigDecimal.valueOf(4050)));
            exchangeRateRepository.save(new ExchangeRate("KHR", "USD", BigDecimal.valueOf(0.00025)));
        }
    }
}

