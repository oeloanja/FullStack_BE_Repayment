package com.billit.repayment.connect.user.client;

import com.billit.repayment.connect.user.dto.UserServiceRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8085")
public interface BorrowTransactionServiceClient {
    @PostMapping("/api/v1/user-service/accounts/transaction/borrow/withdraw")
    ResponseEntity<String> withdrawBorrowAccount(@RequestParam Long userId,
                                            @RequestBody UserServiceRequestDto request);
}

