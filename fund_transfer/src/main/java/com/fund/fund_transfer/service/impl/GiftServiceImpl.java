package com.fund.fund_transfer.service.impl;

import com.fund.fund_transfer.dto.request.GiftRequest;
import com.fund.fund_transfer.dto.request.RedeemGiftRequest;
import com.fund.fund_transfer.dto.response.RedeemGiftResponse;
import com.fund.fund_transfer.dto.response.GiftResponse;
import com.fund.fund_transfer.model.Account;
import com.fund.fund_transfer.model.Gift;
import com.fund.fund_transfer.model.Transaction;
import com.fund.fund_transfer.model.TransactionLog;
import com.fund.fund_transfer.repository.AccountRepository;
import com.fund.fund_transfer.repository.ExchangeRateRepository;
import com.fund.fund_transfer.repository.GiftRepository;
import com.fund.fund_transfer.repository.TransactionRepository;
import com.fund.fund_transfer.service.GiftService;
import com.fund.fund_transfer.utils.CommonMapper;
import com.fund.fund_transfer.utils.HelperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;

import static com.fund.fund_transfer.utils.Constant.*;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {

    private final AccountRepository accountRepository;
    private final GiftRepository giftRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final TransactionRepository transactionRepository;
    private final HelperUtil helperUtil;
    @Override
    public GiftResponse buyGiftCode(GiftRequest req) {
        Account accountOwner = accountRepository.getAccountById(req.getAccountNumber());
        if (accountOwner.getAmount().compareTo(req.getAmount()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        Gift gift = new Gift();
        gift.setGiftCode(HelperUtil.generateGiftCode());
        gift.setAccountOwner(accountOwner);
        gift.setAmount(req.getAmount());
        gift.setRemark(req.getRemark());
        gift.setExpiryDate(HelperUtil.getFutureDate(7));
        gift.setStatus(GIFT_PENDING);
        giftRepository.save(gift);

        accountOwner.setAmount(accountOwner.getAmount().subtract(req.getAmount()));
        accountRepository.save(accountOwner);

        // save transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionId(helperUtil.generateTransactionId());
        transaction.setAccountSender(accountOwner);
        transaction.setAmountDeducted(req.getAmount());
        transaction.setRemark(req.getRemark());
        transaction.setTransactionDate(new Date());
        transactionRepository.save(transaction);

        GiftResponse res = CommonMapper.INSTANCE.giftToDTO(gift);
        return res;
    }

    @Override
    @Transactional
    public RedeemGiftResponse redeemGiftCode(RedeemGiftRequest req) {
        Gift gift = giftRepository.findGiftByGiftCode(req.getGiftCode());
        if(ObjectUtils.isEmpty(gift)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gift Code invalid!");
        }
        if(gift.getStatus() == GIFT_SUCCESS){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gift Code already redeemed!");
        }
        Date currentDate = new Date();
        if(currentDate.compareTo(gift.getExpiryDate()) > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gift Code is expired!");
        }

        Account accountSender = gift.getAccountOwner();
        Account accountReceiver = accountRepository.getAccountById(req.getAccountNumber());

        BigDecimal amount = gift.getAmount();
        if(!accountSender.getCurrency().getCode().equals(accountReceiver.getCurrency().getCode())){
            // cross currency
            BigDecimal rate = exchangeRateRepository.getExchangeRate(accountReceiver.getCurrency().getCode(), accountSender.getCurrency().getCode());
            amount = HelperUtil.convertCurrency(gift.getAmount(), rate);
        }
        accountReceiver.setAmount(accountReceiver.getAmount().add(amount));

        gift.setStatus(GIFT_SUCCESS);
        gift.setAccountRedeem(accountReceiver);
        gift.setRedeemDate(new Date());

        giftRepository.save(gift);
        accountRepository.save(accountReceiver);

        // save transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionId(helperUtil.generateTransactionId());
        transaction.setAccountSender(accountSender);
        transaction.setAccountReceiver(accountReceiver);
        transaction.setAmountDeducted(gift.getAmount());
        transaction.setAmountReceived(amount);
        transaction.setRemark(gift.getRemark());
        transaction.setTransactionDate(new Date());
        transactionRepository.save(transaction);

        RedeemGiftResponse res = new RedeemGiftResponse();
        res.setAccountSender(accountSender.getAccountNumber());
        res.setAccountReceiver(accountReceiver.getAccountNumber());
        res.setAmount(gift.getAmount());
        res.setRemark(res.getRemark());

        return res;
    }
}
