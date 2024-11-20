package com.billit.repayment.controller;

import com.billit.repayment.domain.Repayment;
import com.billit.repayment.dto.RepaymentCreateRequest;
import com.billit.repayment.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ResponseEntity<List<Repayment>> getRepaymentsByLoanId(@PathVariable Integer loanId) {
        return ResponseEntity.ok(repaymentService.getRepaymentsByLoanId(loanId));
    }

    @GetMapping("/investment/{investmentId}")
    public ResponseEntity<List<Repayment>> getRepaymentsByInvestmentId(@PathVariable Integer investmentId) {
        return ResponseEntity.ok(repaymentService.getRepaymentsByInvestmentId(investmentId));
    }

    @PostMapping("/process")
    public ResponseEntity<String> processRepayment(
            @RequestParam Integer loanId,
            @RequestParam Integer investmentId,
            @RequestParam BigDecimal actualRepayment) {
        repaymentService.processRepayment(loanId, investmentId, actualRepayment);
        return ResponseEntity.ok("Repayment processed successfully");
    }
}

