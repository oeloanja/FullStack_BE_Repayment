package com.billit.repayment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RepaymentSuccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer repaymentSuccessId;
    private Integer repaymentId;

    private LocalDateTime paymentDate;
    private Integer repaymentTimes;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}


