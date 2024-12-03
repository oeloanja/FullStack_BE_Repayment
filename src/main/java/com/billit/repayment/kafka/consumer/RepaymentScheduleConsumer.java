package com.billit.repayment.kafka.consumer;

import com.billit.common.event.RepaymentScheduleEvent;
import com.billit.repayment.dto.RepaymentCreateRequest;
import com.billit.repayment.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepaymentScheduleConsumer {
    private final RepaymentService repaymentService;

    @KafkaListener(
            topics = "repayment-schedule",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "repaymentScheduleEventKafkaListenerContainerFactory"
    )
    public void consumeRepaymentSchedule(RepaymentScheduleEvent event) {
        try {
            log.info("Processing repayment schedule for groupId: {}", event.getGroupId());

            event.getGroupLoans().forEach(loan -> {
                RepaymentCreateRequest request = new RepaymentCreateRequest(
                        loan.getLoanId(),
                        loan.getGroupId(),
                        loan.getLoanAmount(),
                        loan.getTerm(),
                        loan.getIntRate(),
                        event.getIssueDate()
                );
                repaymentService.createRepayment(request);
            });

            log.info("Successfully processed repayment schedule for groupId: {}", event.getGroupId());
        } catch (Exception e) {
            log.error("Error processing repayment schedule for groupId: {}", event.getGroupId(), e);
            throw e;
        }
    }
}