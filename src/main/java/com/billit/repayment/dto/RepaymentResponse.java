package com.billit.repayment.dto;

import java.math.BigDecimal;

public class RepaymentResponse {
    private Long repaymentId;
    private BigDecimal repaymentAmount;
    private String status;

    public RepaymentResponse(Long repaymentId, BigDecimal repaymentAmount, String status) {
        this.repaymentId = repaymentId;
        this.repaymentAmount = repaymentAmount;
        this.status = status;
    }

    // Getters and Setters
    public Long getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(Long repaymentId) {
        this.repaymentId = repaymentId;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

