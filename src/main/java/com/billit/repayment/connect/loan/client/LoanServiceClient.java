package com.billit.repayment.connect.loan.client;

import com.billit.repayment.connect.loan.dto.RepaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "LOAN-SERVICE")
public interface LoanServiceClient {
    @GetMapping("/api/v1/loan-service/user")
    RepaymentResponseDto getLoanUserById(@RequestParam Integer loanId);
}
