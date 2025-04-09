package com.fund.fund_transfer.controller;

import com.fund.fund_transfer.dto.request.AccountRequest;
import com.fund.fund_transfer.dto.response.AccountInfoResponse;
import com.fund.fund_transfer.dto.response.AccountRes;
import com.fund.fund_transfer.service.AccountService;
import com.fund.fund_transfer.utils.ApiResponse;
import com.fund.fund_transfer.utils.HelperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;

import static com.fund.fund_transfer.utils.Constant.API_PATH;

@RestController
@RequestMapping(API_PATH+"/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<AccountInfoResponse>> getAccountInfo(Principal principal) {
        try {
            AccountInfoResponse accountInfo = accountService.getAccountsByUserId(principal.getName());
            return new ResponseEntity<>(new ApiResponse<>(accountInfo), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<AccountRes>> accountCreate(@Valid @RequestBody AccountRequest req, BindingResult result, Principal principal) {
        AccountRes res;
        try {
            // validation error
            if (result.hasErrors()) {
                HashMap<String, String> errors = HelperUtil.getValidationErrors(result);
                return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.BAD_REQUEST);
            }
            res = accountService.createAccount(req, principal);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ApiResponse<>(res), HttpStatus.OK);
    }
}
