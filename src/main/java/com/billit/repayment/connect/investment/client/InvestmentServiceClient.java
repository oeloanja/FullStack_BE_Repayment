package com.billit.repayment.connect.investment.client;

import com.billit.repayment.connect.investment.dto.InvestmentServiceRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "INVESTMENT-SERVICE")
public interface InvestmentServiceClient {
    @PostMapping("/api/v1/invest-service/investments/deposit-settlement")
    ResponseEntity<String> depositSettlementAmount(@RequestBody InvestmentServiceRequestDTO request);

    @PutMapping("/api/v1/invest-service/investments/{groupId}/status/update")
    ResponseEntity<String> updateInvestmentStatusByGroupId(
            @PathVariable Integer groupId,
            @RequestParam InvestStatusType statusType);
}
