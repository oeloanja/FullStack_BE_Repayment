package com.billit.repayment.connect.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentResponseDto {
    private UUID userBorrowId;
    private Integer accountBorrowId;
}
