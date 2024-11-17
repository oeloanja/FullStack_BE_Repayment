package com.billit.repayment.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class RepaymentFail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "repayment_id")
    private Repayment repayment;

    private LocalDateTime paymentDate;  // 실패한 상환 날짜
    private BigDecimal repaymentLeft;  // 남은 금액
    private Integer retryCount;        // 재시도 횟수

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Repayment getRepayment() {
        return repayment;
    }

    public void setRepayment(Repayment repayment) {
        this.repayment = repayment;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getRepaymentLeft() {
        return repaymentLeft;
    }

    public void setRepaymentLeft(BigDecimal repaymentLeft) {
        this.repaymentLeft = repaymentLeft;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }
}

