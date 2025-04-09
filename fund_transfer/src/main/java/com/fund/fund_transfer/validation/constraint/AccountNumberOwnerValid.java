package com.fund.fund_transfer.validation.constraint;

import com.fund.fund_transfer.validation.AccountNumberOwnerValidValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AccountNumberOwnerValidValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountNumberOwnerValid {
    String message() default "Your Account Number is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

