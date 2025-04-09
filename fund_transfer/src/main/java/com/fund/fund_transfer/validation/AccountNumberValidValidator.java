package com.fund.fund_transfer.validation;

import com.fund.fund_transfer.repository.AccountRepository;
import com.fund.fund_transfer.validation.constraint.AccountNumberValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountNumberValidValidator implements ConstraintValidator<AccountNumberValid, String> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean isValid(String accountNumber, ConstraintValidatorContext context) {
        if (accountNumber == null || accountNumber.isEmpty()) return true;
        return accountRepository.existsByAccountNumber(accountNumber);
    }
}
