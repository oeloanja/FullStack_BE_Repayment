package com.billit.repayment.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RepaymentResponse {
    private Integer repaymentId;
    private Integer loanId;
    private Integer investmentId;
    private BigDecimal repaymentPrincipal;
    private BigDecimal repaymentInterest;
    private Integer dueDate;
}


