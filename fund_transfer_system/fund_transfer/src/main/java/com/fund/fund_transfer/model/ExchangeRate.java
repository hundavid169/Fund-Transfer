package com.fund.fund_transfer.model;

import com.fund.fund_transfer.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "exchange_rate")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_from", nullable = false, length = 3)
    private String currencyFrom;

    @Column(name = "currency_to", nullable = false, length = 3)
    private String currencyTo;

    @Column(name = "rate", nullable = false, precision = 15, scale = 5)
    private BigDecimal rate;

    public ExchangeRate(String currencyFrom, String currencyTo, BigDecimal rate) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.rate = rate;
    }
}
