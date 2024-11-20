package com.billit.repayment.service;

import com.billit.repayment.domain.Repayment;
import com.billit.repayment.domain.RepaymentFail;
import com.billit.repayment.domain.RepaymentSuccess;
import com.billit.repayment.dto.RepaymentCreateRequest;
import com.billit.repayment.repository.RepaymentFailRepository;
import com.billit.repayment.repository.RepaymentRepository;
import com.billit.repayment.repository.RepaymentSuccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepaymentService {
    private final RepaymentRepository repaymentRepository;
    private final RepaymentSuccessRepository repaymentSuccessRepository;
    private final RepaymentFailRepository repaymentFailRepository;

    public Repayment createRepayment(Integer loanId, Integer investmentId) {
        // LoanServiceClient를 통해 대출 정보 가져오기
        BigDecimal loanAmount = loanServiceClient.getLoanAmount(loanId);
        BigDecimal interestRate = loanServiceClient.getInterestRate(loanId);
        LocalDate issueDate = loanServiceClient.getIssueDate(loanId);

        // 상환 데이터 계산
        BigDecimal repaymentPrincipal = loanAmount.divide(new BigDecimal(12), 2, RoundingMode.HALF_UP);
        BigDecimal repaymentInterest = repaymentPrincipal.multiply(interestRate)
                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

        // 상환 예정일 계산 (발행일 기준 + 회차)
        Integer dueDate = issueDate.getDayOfMonth(); // 예: 매월 특정 일

        // Repayment 객체 생성
        Repayment repayment = new Repayment();
        repayment.setLoanId(loanId);
        repayment.setInvestmentId(investmentId);
        repayment.setRepaymentPrincipal(repaymentPrincipal);
        repayment.setRepaymentInterest(repaymentInterest);
        repayment.setDueDate(dueDate);
        repayment.setCreatedAt(LocalDateTime.now());

        return repaymentRepository.save(repayment);
    }

    public List<Repayment> getAllRepayments() {
        return repaymentRepository.findAll();
    }

    public List<Repayment> getRepaymentsByLoanId(Integer loanId) {
        return repaymentRepository.findByLoanId(loanId);
    }

    public List<Repayment> getRepaymentsByInvestmentId(Integer investmentId) {
        return repaymentRepository.findByInvestmentId(investmentId);
    }

    public void processRepayment(Integer loanId, Integer investmentId, BigDecimal actualRepayment) {
        Repayment repayment = repaymentRepository.findByLoanIdAndInvestmentId(loanId, investmentId)
                .orElseThrow(() -> new IllegalArgumentException("Repayment not found"));

        // 상환 원금과 이자를 계산
        BigDecimal expectedTotal = repayment.getRepaymentPrincipal()
                .add(repayment.getRepaymentInterest());

        // 잔액 계산
        BigDecimal remainingAmount = actualRepayment.subtract(expectedTotal);

        if (remainingAmount.compareTo(BigDecimal.ZERO) >= 0) {
            // 상환 성공
            RepaymentSuccess success = new RepaymentSuccess();
            success.setRepaymentId(repayment.getRepaymentId());
            success.setPaymentDate(LocalDateTime.now());
            success.setRepaymentTimes(repayment.getDueDate()); // 회차 처리
            repaymentSuccessRepository.save(success);

        } else {
            // 상환 실패
            RepaymentFail fail = new RepaymentFail();
            fail.setRepaymentId(repayment.getRepaymentId());
            fail.setPaymentDate(LocalDateTime.now());
            fail.setRepaymentLeft(remainingAmount.abs()); // 남은 금액을 양수로 저장
            fail.setRepaymentTimes(repayment.getDueDate()); // 회차 처리
            repaymentFailRepository.save(fail);
        }
    }
}