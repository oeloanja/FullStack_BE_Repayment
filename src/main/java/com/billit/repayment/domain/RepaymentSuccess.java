package com.billit.repayment.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class RepaymentSuccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "repayment_id")
    private Repayment repayment;

    private LocalDateTime paymentDate;  // 실제 상환일
    private BigDecimal repaymentLeft;  // 남은 잔액
    private Integer repaymentTimes;    // 상환 회차

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

    public Integer getRepaymentTimes() {
        return repaymentTimes;
    }

    public void setRepaymentTimes(Integer repaymentTimes) {
        this.repaymentTimes = repaymentTimes;
    }
}

