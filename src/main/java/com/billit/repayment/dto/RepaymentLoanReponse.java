package com.billit.repayment.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class RepaymentLoanReponse {
    private Integer repaymentTimes;
    private LocalDateTime paymentDate;
    private BigDecimal repaymentAmount;
    private boolean isRepaymentSuccess;
}
