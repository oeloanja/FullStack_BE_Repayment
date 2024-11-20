package com.billit.repayment.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class LoanServiceClient {
    private final RestTemplate restTemplate;

    public BigDecimal getLoanAmount(Integer loanId) {
        String url = "http://loan-service/api/loans/" + loanId + "/amount";
        return restTemplate.getForObject(url, BigDecimal.class);
    }

    public BigDecimal getInterestRate(Integer loanId) {
        String url = "http://loan-service/api/loans/" + loanId + "/interest-rate";
        return restTemplate.getForObject(url, BigDecimal.class);
    }

    public LocalDate getIssueDate(Integer loanId) {
        String url = "http://loan-service/api/loans/" + loanId + "/issue-date";
        return restTemplate.getForObject(url, LocalDate.class);
    }
}

