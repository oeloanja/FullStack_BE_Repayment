package com.billit.repayment.connect.investment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentServiceRequestDTO {
    private Integer groupId;
    private BigDecimal repaymentPrincipal;
    private BigDecimal repaymentInterest;
}
