package com.billit.repayment.connect.investment.client;

import com.billit.repayment.connect.investment.dto.InvestmentServiceRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "invest-service", url = "http://localhost:8081")
public interface InvestmentServiceClient {
    @PostMapping("/api/v1/invest-service/investments/deposit-settlement")
    ResponseEntity<String> depositSettlementAmount(@RequestBody InvestmentServiceRequestDTO request);
}
