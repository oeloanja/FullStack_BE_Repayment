package com.billit.repayment.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class RepaymentProcessCreateRequest {
    private Integer loanId;
    private BigDecimal actualRepaymentAmount;
}
