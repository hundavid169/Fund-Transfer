package com.fund.fund_transfer.controller;

import com.fund.fund_transfer.dto.request.UserRequest;
import com.fund.fund_transfer.dto.response.UserRes;
import com.fund.fund_transfer.model.User;
import com.fund.fund_transfer.service.UserService;
import com.fund.fund_transfer.utils.ApiResponse;
import com.fund.fund_transfer.utils.HelperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static com.fund.fund_transfer.utils.Constant.API_PATH;

@RestController
@RequestMapping(API_PATH+"/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse<UserRes>> userRegister(@Valid @RequestBody UserRequest userReq, BindingResult result) {
        UserRes userRes;
        try {
            // validation error
            if (result.hasErrors()) {
                HashMap<String, String> errors = HelperUtil.getValidationErrors(result);
                return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.BAD_REQUEST);
            }
            userRes = userService.userRegister(userReq);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ApiResponse<>(userRes), HttpStatus.OK);
    }

}

