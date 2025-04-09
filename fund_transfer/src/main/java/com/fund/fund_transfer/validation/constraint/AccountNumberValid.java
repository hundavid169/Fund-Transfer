package com.fund.fund_transfer.validation.constraint;

import com.fund.fund_transfer.validation.AccountNumberValidValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AccountNumberValidValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountNumberValid {
    String message() default "Account Number is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

