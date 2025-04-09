package com.fund.fund_transfer.utils;

import com.fund.fund_transfer.repository.TransactionLogRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
@Component
public class HelperUtil {
    private static final Logger log = LogManager.getLogger(HelperUtil.class);

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    public static HashMap<String, String> getValidationErrors(BindingResult result) {
        HashMap<String, String> errors = new HashMap<>();
        try {
            for (ObjectError error : result.getAllErrors()) {
                String field = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(field, message);
            }
        } catch (Exception e) {
            log.error("getValidationErrors", e);
        }
        return errors;
    }

    public static String incrementAccountNumber(Integer maxNumber) {
        if(maxNumber == null){
            maxNumber = 0;
        }
        int length = 6;
        maxNumber++;

        return String.format("%0" + length + "d", maxNumber);
    }

    public static BigDecimal convertCurrency(BigDecimal amount, BigDecimal exchangeRate){
        return amount.multiply(exchangeRate).setScale(5, RoundingMode.HALF_UP);
    }

    public String generateTransactionId() {
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        long count = Optional.ofNullable(transactionLogRepository.countTodayTransactions()).orElse(0L);
        count++;
        return String.format("TRX-%s-%06d", today, count);
    }

    public static Date getFutureDate(int nextDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, nextDay);

        Date nextDate = calendar.getTime();
        return nextDate;
    }

    public static String generateGiftCode() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int CODE_LENGTH = 12; // customize length
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return sb.toString();
    }

}
