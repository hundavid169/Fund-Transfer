package com.fund.fund_transfer.validation;

import com.fund.fund_transfer.repository.AccountRepository;
import com.fund.fund_transfer.validation.constraint.AccountNumberOwnerValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccountNumberOwnerValidValidator implements ConstraintValidator<AccountNumberOwnerValid, String> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean isValid(String accountNumber, ConstraintValidatorContext context) {
        if (accountNumber == null || accountNumber.isEmpty()) return true;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return accountRepository.existsByAccountNumberAndUserUserUuid(accountNumber, authentication.getName());
    }
}
