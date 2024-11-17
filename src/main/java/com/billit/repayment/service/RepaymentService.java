package com.billit.repayment.service;

import com.billit.repayment.domain.Repayment;
import com.billit.repayment.domain.RepaymentFail;
import com.billit.repayment.domain.RepaymentSuccess;
import com.billit.repayment.dto.RepaymentRequest;
import com.billit.repayment.dto.RepaymentResponse;
import com.billit.repayment.repository.RepaymentFailRepository;
import com.billit.repayment.repository.RepaymentRepository;
import com.billit.repayment.repository.RepaymentSuccessRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepaymentService {

    private final RepaymentRepository repaymentRepository;
    private final RepaymentSuccessRepository repaymentSuccessRepository;
    private final RepaymentFailRepository repaymentFailRepository;

    public RepaymentService(RepaymentRepository repaymentRepository,
                            RepaymentSuccessRepository repaymentSuccessRepository,
                            RepaymentFailRepository repaymentFailRepository) {
        this.repaymentRepository = repaymentRepository;
        this.repaymentSuccessRepository = repaymentSuccessRepository;
        this.repaymentFailRepository = repaymentFailRepository;
    }

    public RepaymentResponse createRepayment(RepaymentRequest request) {
        Repayment repayment = new Repayment();
        repayment.setLoanId(request.getLoanId());
        repayment.setInvestmentId(request.getInvestmentId());
        repayment.setRepaymentAmount(request.getRepaymentAmount());
        repayment.setDueDate(request.getDueDate());
        repayment.setCreatedAt(request.getCreatedAt());

        Repayment savedRepayment = repaymentRepository.save(repayment);

        return new RepaymentResponse(savedRepayment.getId(), savedRepayment.getRepaymentAmount(), "Created");
    }

    public List<RepaymentResponse> getRepaymentsByLoanId(Long loanId) {
        List<Repayment> repayments = repaymentRepository.findByLoanId(loanId);
        return repayments.stream()
                .map(r -> new RepaymentResponse(r.getId(), r.getRepaymentAmount(), "Pending"))
                .collect(Collectors.toList());
    }

    public void updateRepaymentStatus(Long repaymentId, String status) {
        Repayment repayment = repaymentRepository.findById(repaymentId)
                .orElseThrow(() -> new IllegalArgumentException("Repayment not found"));

        if ("SUCCESS".equalsIgnoreCase(status)) {
            RepaymentSuccess success = new RepaymentSuccess();
            success.setRepayment(repayment);
            success.setRepaymentLeft(repayment.getRepaymentAmount().subtract(repayment.getRepaymentAmount())); // 예시 계산
            repaymentSuccessRepository.save(success);
        } else if ("FAIL".equalsIgnoreCase(status)) {
            RepaymentFail fail = new RepaymentFail();
            fail.setRepayment(repayment);
            fail.setRepaymentLeft(repayment.getRepaymentAmount()); // 남은 금액 처리
            repaymentFailRepository.save(fail);
        }
    }
}

