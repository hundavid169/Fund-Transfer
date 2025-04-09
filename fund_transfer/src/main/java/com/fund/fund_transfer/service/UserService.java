package com.fund.fund_transfer.service;

import com.fund.fund_transfer.dto.request.UserRequest;
import com.fund.fund_transfer.dto.response.UserRes;
import com.fund.fund_transfer.model.User;

import java.util.List;
import java.util.Optional;
public interface UserService {

    List<User> getAllUsers();

    UserRes userRegister(UserRequest userReq);

}
