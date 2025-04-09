package com.fund.fund_transfer.repository;

import com.fund.fund_transfer.model.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    Gift findGiftByGiftCode(String giftCode);
}
