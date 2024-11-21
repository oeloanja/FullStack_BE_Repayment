package com.billit.repayment.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RepaymentCreateRequest {
    private Integer loanId;
    private Integer groupId;
    private BigDecimal loanAmount;
    private Integer term;
    private BigDecimal intRate;
    private LocalDate issueDate;
}


