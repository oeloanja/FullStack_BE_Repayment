package com.billit.repayment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RepaymentFail {
    @Id
    private Integer repaymentId;

    private LocalDateTime paymentDate;
    private BigDecimal repaymentLeft;
    private Integer repaymentTimes;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}


