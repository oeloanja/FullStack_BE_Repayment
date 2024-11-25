package com.billit.repayment.connect.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentResponseDto {
    private Integer userBorrowId;
    private Integer accountBorrowId;
}
