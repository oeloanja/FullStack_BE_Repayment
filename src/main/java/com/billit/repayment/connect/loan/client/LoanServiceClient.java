package com.billit.repayment.connect.loan.client;

import com.billit.repayment.connect.loan.dto.LoanStatusRequestDto;
import com.billit.repayment.connect.loan.dto.LoanStatusResponseDto;
import com.billit.repayment.connect.loan.dto.RepaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "LOAN-SERVICE")
public interface LoanServiceClient {
    @GetMapping("/api/v1/loan-service/user")
    RepaymentResponseDto getLoanUserById(@RequestParam Integer loanId);

    @PutMapping("/api/v1/loan-service/status")
    LoanStatusResponseDto updateLoanStatus(@RequestBody LoanStatusRequestDto request);

    @GetMapping("/api/v1/loan-service/{groupId}/status")
    boolean isLoanGroupStatusExecuting(@PathVariable Integer groupId);
}
