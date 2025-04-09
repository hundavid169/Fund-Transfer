package com.fund.fund_transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FundTransferApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundTransferApplication.class, args);
    }

}
