package com.fund.fund_transfer.utils;

import com.fund.fund_transfer.dto.request.AccountRequest;
import com.fund.fund_transfer.dto.request.UserRequest;
import com.fund.fund_transfer.dto.response.AccountRes;
import com.fund.fund_transfer.dto.response.GiftResponse;
import com.fund.fund_transfer.dto.response.UserRes;
import com.fund.fund_transfer.model.Account;
import com.fund.fund_transfer.model.Gift;
import com.fund.fund_transfer.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CommonMapper {
    CommonMapper INSTANCE = Mappers.getMapper(CommonMapper.class);

    User userToEntity(UserRequest dto);
    @Mapping(source = "userUuid", target = "userUuid")
    UserRes userToDTO(User user);

    @Mapping(source = "currency.code", target = "currency")
    AccountRes accountToDTO(Account account);

    @Mapping(source = "currency", target = "currency.code")
    Account accountToEntity(AccountRequest dto);
    List<AccountRes> accountListToDTOList(List<Account> accounts);

    @Mapping(source = "accountOwner.accountNumber", target = "accountNumber")
    @Mapping(source = "accountOwner.currency.code", target = "currency")
    GiftResponse giftToDTO(Gift gift);
}
