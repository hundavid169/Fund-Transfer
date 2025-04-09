package com.fund.fund_transfer.service;

import com.fund.fund_transfer.dto.request.AccountRequest;
import com.fund.fund_transfer.dto.response.AccountInfoResponse;
import com.fund.fund_transfer.dto.response.AccountRes;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface AccountService {
    AccountInfoResponse getAccountsByUserId(String userUuid);
    AccountRes createAccount(AccountRequest request, Principal principal);
}
