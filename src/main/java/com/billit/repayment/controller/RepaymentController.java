package com.billit.repayment.controller;

import com.billit.repayment.dto.RepaymentRequest;
import com.billit.repayment.dto.RepaymentResponse;
import com.billit.repayment.service.RepaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repayments")
public class RepaymentController {

    private final RepaymentService repaymentService;

    public RepaymentController(RepaymentService repaymentService) {
        this.repaymentService = repaymentService;
    }

    @PostMapping
    public ResponseEntity<RepaymentResponse> createRepayment(@RequestBody RepaymentRequest request) {
        RepaymentResponse response = repaymentService.createRepayment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<List<RepaymentResponse>> getRepaymentsByLoan(@PathVariable Long loanId) {
        List<RepaymentResponse> repayments = repaymentService.getRepaymentsByLoanId(loanId);
        return ResponseEntity.ok(repayments);
    }

    @PatchMapping("/{repaymentId}/status")
    public ResponseEntity<Void> updateRepaymentStatus(@PathVariable Long repaymentId, @RequestParam String status) {
        repaymentService.updateRepaymentStatus(repaymentId, status);
        return ResponseEntity.noContent().build();
    }
}

