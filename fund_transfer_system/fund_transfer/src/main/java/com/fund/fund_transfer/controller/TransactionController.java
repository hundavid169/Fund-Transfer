package com.fund.fund_transfer.controller;

import com.fund.fund_transfer.dto.response.AccountInfoResponse;
import com.fund.fund_transfer.dto.response.TransactionResponse;
import com.fund.fund_transfer.service.AccountService;
import com.fund.fund_transfer.service.TransactionService;
import com.fund.fund_transfer.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static com.fund.fund_transfer.utils.Constant.API_PATH;

@RestController
@RequestMapping(API_PATH+"/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/view/{accountNumber}")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactions(@PathVariable String accountNumber, Principal principal) {
        try {
            List<TransactionResponse> transactionResponses = transactionService.getTransactions(accountNumber, principal);
            return new ResponseEntity<>(new ApiResponse<>(transactionResponses), HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(new ApiResponse<>(ex.getStatusCode().value(), ex.getReason()), ex.getStatusCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{accountNumber}")
    public ResponseEntity<InputStreamResource> downloadTransactions(@PathVariable String accountNumber, Principal principal) throws IOException {
        List<TransactionResponse> transactionResponses = transactionService.getTransactions(accountNumber, principal);
        ByteArrayInputStream in = transactionService.transactionsToExcel(transactionResponses);

        String filename = "transactions_" + accountNumber + ".xlsx";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename="+filename);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
}
