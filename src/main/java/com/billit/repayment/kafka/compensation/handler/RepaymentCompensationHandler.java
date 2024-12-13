package com.billit.repayment.kafka.compensation.handler;

import com.billit.repayment.domain.Repayment;
import com.billit.repayment.kafka.compensation.event.RepaymentScheduleCompensationEvent;
import com.billit.repayment.repository.RepaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepaymentCompensationHandler {
    private final RepaymentRepository repaymentRepository;

    @KafkaListener(topics = "repayment-schedule-compensation")
    public void handleRepaymentScheduleCompensation(RepaymentScheduleCompensationEvent event) {
        try {
            List<Repayment> repayments = repaymentRepository.findByGroupId(event.getGroupId());
            if (!repayments.isEmpty()) {
                repaymentRepository.deleteAll(repayments);
                log.info("Successfully cancelled repayment schedule for groupId: {}", event.getGroupId());
            }
        } catch (Exception e) {
            log.error("Failed to cancel repayment schedule", e);
            throw e;
        }
    }
}
