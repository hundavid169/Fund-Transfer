package com.fund.fund_transfer.service.impl;

import com.fund.fund_transfer.dto.request.AccountRequest;
import com.fund.fund_transfer.dto.response.AccountInfoResponse;
import com.fund.fund_transfer.dto.response.AccountRes;
import com.fund.fund_transfer.model.Account;
import com.fund.fund_transfer.model.Currency;
import com.fund.fund_transfer.model.User;
import com.fund.fund_transfer.repository.AccountRepository;
import com.fund.fund_transfer.repository.CurrencyRepository;
import com.fund.fund_transfer.repository.UserRepository;
import com.fund.fund_transfer.service.AccountService;
import com.fund.fund_transfer.utils.CommonMapper;
import com.fund.fund_transfer.utils.HelperUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static com.fund.fund_transfer.utils.Constant.CURRENCY_KHR;
import static com.fund.fund_transfer.utils.Constant.CURRENCY_USD;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LogManager.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    @Override
    public AccountInfoResponse getAccountsByUserId(String userUuid) {
        AccountInfoResponse accountInfo = new AccountInfoResponse();
        List<AccountRes> accountsRes = null;
        try {
            List<Account> accounts = accountRepository.findByUserUserUuidOrderByAccountNumberAsc(userUuid);
            accountsRes = CommonMapper.INSTANCE.accountListToDTOList(accounts);

            BigDecimal totalAmountUSD = getTotalAmount(userUuid, CURRENCY_USD);
            BigDecimal totalAmountKHR = getTotalAmount(userUuid, CURRENCY_KHR);

            accountInfo.setTotalAmountUSD(totalAmountUSD);
            accountInfo.setTotalAmountKHR(totalAmountKHR);
            accountInfo.setAccounts(accountsRes);
        } catch (Exception ex) {
            log.error("getAccountsByUserId", ex);
        }
        return accountInfo;
    }

    @Override
    public AccountRes createAccount(AccountRequest request, Principal principal) {
        AccountRes res = null;
        try {
            Account account = CommonMapper.INSTANCE.accountToEntity(request);
            User user = new User(principal.getName());
            Currency currency = currencyRepository.findCurrencyByCode(request.getCurrency());

            account.setUser(user);
            account.setCurrency(currency);
            account.setAccountNumber(HelperUtil.incrementAccountNumber(accountRepository.findMaxAccountNumber()));

            account = accountRepository.save(account);
            res = CommonMapper.INSTANCE.accountToDTO(account);

        } catch (Exception ex) {
            log.error("createAccount", ex);
        }
        return res;
    }

    private BigDecimal getTotalAmount(String userUuid, String currency){
        BigDecimal totalAmount = accountRepository.totalAmountByCurrency(userUuid, currency);
        return (totalAmount != null) ? totalAmount : BigDecimal.ZERO;
    }
}
