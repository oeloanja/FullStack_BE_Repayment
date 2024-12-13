package com.billit.repayment.kafka.compensation.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RepaymentScheduleCompensationEvent {
    private Integer groupId;
}