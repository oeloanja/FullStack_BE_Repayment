package com.billit.repayment.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepaymentScheduleEvent {
    private List<LoanResponseClientEventDto> groupLoans;
    private Integer groupId;
    private LocalDate issueDate;
    private String status;
}
