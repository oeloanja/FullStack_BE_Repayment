package com.billit.repayment.connect.loan.dto;

import com.billit.repayment.connect.loan.client.LoanStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanStatusResponseDto {
    private final Integer loanId;
    private final LoanStatusType statusType;
}
