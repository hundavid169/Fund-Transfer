package com.fund.fund_transfer.validation;

import com.fund.fund_transfer.repository.UserRepository;
import com.fund.fund_transfer.validation.constraint.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) return true;
        return !userRepository.existsByEmail(email);
    }
}
