package com.fund.fund_transfer.controller;

import com.fund.fund_transfer.dto.request.GiftRequest;
import com.fund.fund_transfer.dto.request.RedeemGiftRequest;
import com.fund.fund_transfer.dto.response.RedeemGiftResponse;
import com.fund.fund_transfer.dto.response.GiftResponse;
import com.fund.fund_transfer.service.GiftService;
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

import java.security.Principal;
import java.util.HashMap;

import static com.fund.fund_transfer.utils.Constant.API_PATH;

@RestController
@RequestMapping(API_PATH+"/gifts")
public class GiftController {

    @Autowired
    private GiftService giftService;

    @PostMapping(value = "/buy")
    public ResponseEntity<ApiResponse<GiftResponse>> buyGiftCode(@Valid @RequestBody GiftRequest req, BindingResult result, Principal principal) {
        GiftResponse res;
        try {
            // validation error
            if (result.hasErrors()) {
                HashMap<String, String> errors = HelperUtil.getValidationErrors(result);
                return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.BAD_REQUEST);
            }
            res = giftService.buyGiftCode(req);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(new ApiResponse<>(ex.getStatusCode().value(), ex.getReason()), ex.getStatusCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ApiResponse<>(res), HttpStatus.OK);
    }

    @PostMapping(value = "/redeem")
    public ResponseEntity<ApiResponse<RedeemGiftResponse>> redeemGiftCode(@Valid @RequestBody RedeemGiftRequest req, BindingResult result, Principal principal) {
        RedeemGiftResponse res;
        try {
            // validation error
            if (result.hasErrors()) {
                HashMap<String, String> errors = HelperUtil.getValidationErrors(result);
                return new ResponseEntity<>(new ApiResponse<>(errors), HttpStatus.BAD_REQUEST);
            }
            res = giftService.redeemGiftCode(req);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(new ApiResponse<>(ex.getStatusCode().value(), ex.getReason()), ex.getStatusCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ApiResponse<>(res), HttpStatus.OK);
    }
}
