package com.billit.repayment.connect.user.client;

import com.billit.repayment.config.FeignConfig;
import com.billit.repayment.connect.user.dto.UserServiceRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE",
        configuration = FeignConfig.class,
        path = "/api/v1/user-service", url = "${feign.client.config.user-service.url}")
public interface BorrowTransactionServiceClient {
    @PostMapping("/accounts/transaction/borrow/withdraw")
    ResponseEntity<String> withdrawBorrowAccount(@RequestParam Long userId,
                                            @RequestBody UserServiceRequestDto request);
}

