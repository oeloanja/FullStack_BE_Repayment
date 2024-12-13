package com.billit.repayment.connect.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanStatusRequestDto {
    private Integer loanId;
    private int status;
}

