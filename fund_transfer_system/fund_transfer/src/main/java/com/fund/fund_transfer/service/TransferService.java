package com.fund.fund_transfer.service;

import com.fund.fund_transfer.dto.request.TransferRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public interface TransferService {
    TransferRequest transferMoney(TransferRequest req) throws Exception;
}
