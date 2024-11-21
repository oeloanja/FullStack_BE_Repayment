package com.billit.repayment.controller;

import com.billit.repayment.domain.Repayment;
import com.billit.repayment.dto.RepaymentCreateRequest;
import com.billit.repayment.dto.RepaymentLoanReponse;
import com.billit.repayment.dto.RepaymentProcessCreateRequest;
import com.billit.repayment.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/repayments")
@RequiredArgsConstructor
public class RepaymentController {

    private final RepaymentService repaymentService;
    @PostMapping
    public ResponseEntity<Repayment> createRepayment(@RequestBody RepaymentCreateRequest request) {
        return ResponseEntity.ok(repaymentService.createRepayment(request));
    }

    @GetMapping
    public ResponseEntity<List<Repayment>> getAllRepayments() {
        return ResponseEntity.ok(repaymentService.getAllRepayments());
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<RepaymentLoanReponse>> getRepaymentsByLoanId(@PathVariable Integer loanId) {
        return ResponseEntity.ok(repaymentService.getRepaymentsByLoanId(loanId));
    }

    @GetMapping("/loan/{loanId}/latest")
    public ResponseEntity<LocalDateTime> getLatestPaymentDateByLoanId(@PathVariable Integer loanId) {
        LocalDateTime latestPaymentDate = repaymentService.getLatestPaymentDateByLoanId(loanId);
        return ResponseEntity.ok(latestPaymentDate);
    }

    // 상환 진행 -> 성공/실패 테이블 생성
    @PostMapping("/process")
    public ResponseEntity<String> createRepaymentProcess(@RequestBody RepaymentProcessCreateRequest request) {
        repaymentService.createRepaymentProcess(request);
        return ResponseEntity.ok("Repayment processed successfully");
    }
}

