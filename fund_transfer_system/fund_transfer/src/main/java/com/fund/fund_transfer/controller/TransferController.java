package com.fund.fund_transfer.controller;

import com.fund.fund_transfer.dto.request.TransferRequest;
import com.fund.fund_transfer.service.TransferService;
import com.fund.fund_transfer.utils.ApiResponse;
import com.fund.fund_transfer.utils.HelperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

import static com.fund.fund_transfer.utils.Constant.API_PATH;

@RestController
@RequestMapping(API_PATH+"/transfer")
public class TransferController {
    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransferRequest>> transferMoney(@Valid @RequestBody TransferRequest req, BindingResult result) {
        TransferRequest res;
        try {
            // validation error
            if (result.hasErrors()) {
                HashMap<String, String> errors = HelperUtil.getValidationErrors(result);
                return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.BAD_REQUEST);
            }
            res = transferService.transferMoney(req);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(new ApiResponse<>(ex.getStatusCode().value(), ex.getReason()), ex.getStatusCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Money Transfer Successfully!", res), HttpStatus.OK);
    }
}
