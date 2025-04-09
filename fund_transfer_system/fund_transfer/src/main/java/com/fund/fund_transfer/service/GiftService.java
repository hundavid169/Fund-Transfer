package com.fund.fund_transfer.service;

import com.fund.fund_transfer.dto.request.GiftRequest;
import com.fund.fund_transfer.dto.request.RedeemGiftRequest;
import com.fund.fund_transfer.dto.response.RedeemGiftResponse;
import com.fund.fund_transfer.dto.response.GiftResponse;
import org.springframework.stereotype.Service;

@Service
public interface GiftService {
    GiftResponse buyGiftCode(GiftRequest req);
    RedeemGiftResponse redeemGiftCode(RedeemGiftRequest req);
}
