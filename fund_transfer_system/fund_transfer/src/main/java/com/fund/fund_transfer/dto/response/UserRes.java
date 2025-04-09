package com.fund.fund_transfer.dto.response;

import lombok.Data;

import java.util.HashMap;

@Data
public class UserRes {
    private String userUuid;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String tel;
    private String idCard;
}
