package com.billit.repayment.service;

import com.billit.repayment.domain.Repayment;
import com.billit.repayment.domain.RepaymentFail;
import com.billit.repayment.domain.RepaymentSuccess;
import com.billit.repayment.dto.RepaymentCreateRequest;
import com.billit.repayment.dto.RepaymentLoanReponse;
import com.billit.repayment.dto.RepaymentProcessCreateRequest;
import com.billit.repayment.repository.RepaymentFailRepository;
import com.billit.repayment.repository.RepaymentRepository;
import com.billit.repayment.repository.RepaymentSuccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RepaymentService {
    private final RepaymentRepository repaymentRepository;
    private final RepaymentSuccessRepository repaymentSuccessRepository;
    private final RepaymentFailRepository repaymentFailRepository;

    //현숙언니가 대출자에게 대출금 입금을 하면서 함께 불러줘야하는 api
    public Repayment createRepayment(RepaymentCreateRequest request) {
        BigDecimal repaymentPrincipal = request.getLoanAmount().divide(BigDecimal.valueOf(request.getTerm()), 2, RoundingMode.HALF_UP);
        BigDecimal repaymentInterest = repaymentPrincipal
                .multiply(request.getIntRate().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP).divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP));

        Integer dueDate = request.getIssueDate().getDayOfMonth();

        Repayment repayment = new Repayment();
        repayment.setLoanId(request.getLoanId());
        repayment.setGroupId(request.getGroupId());
        repayment.setRepaymentPrincipal(repaymentPrincipal);
        repayment.setRepaymentInterest(repaymentInterest);
        repayment.setDueDate(dueDate);
        repayment.setCreatedAt(LocalDateTime.now());

        return repaymentRepository.save(repayment);
    }

    public List<Repayment> getAllRepayments() {
        return repaymentRepository.findAll();
    }

    public List<RepaymentLoanReponse> getRepaymentsByLoanId(Integer loanId) {
        Repayment repayment = repaymentRepository.findByLoanId(loanId)
                .orElseThrow(() -> new NoSuchElementException("No Repayment found for loan id: " + loanId));

        List<RepaymentLoanReponse> responses = new ArrayList<>();
        Integer repaymentId = repayment.getRepaymentId();

        List<RepaymentSuccess> repaymentSuccesses = repaymentSuccessRepository.findRepaymentSuccessByRepaymentId(repaymentId);
        repaymentSuccesses.forEach(success -> {
            BigDecimal repaymentAmount = repayment.getRepaymentPrincipal()
                    .add(repayment.getRepaymentInterest());

            responses.add(RepaymentLoanReponse.builder()
                    .repaymentTimes(success.getRepaymentTimes())
                    .paymentDate(success.getPaymentDate())
                    .repaymentAmount(repaymentAmount)
                    .isRepaymentSuccess(true)
                    .build());
        });

        List<RepaymentFail> repaymentFails = repaymentFailRepository.findRepaymentFailByRepaymentId(repaymentId);
        repaymentFails.forEach(fail -> {
            BigDecimal repaymentAmount = repayment.getRepaymentPrincipal()
                    .add(repayment.getRepaymentInterest())
                    .subtract(fail.getRepaymentLeft());

            responses.add(RepaymentLoanReponse.builder()
                    .repaymentTimes(fail.getRepaymentTimes())
                    .paymentDate(fail.getPaymentDate())
                    .repaymentAmount(repaymentAmount)
                    .isRepaymentSuccess(false)
                    .build());
        });

        return responses;
    }

    public LocalDateTime getLatestPaymentDateByLoanId(Integer loanId) {
        Repayment repayment = repaymentRepository.findByLoanId(loanId)
                .orElseThrow(() -> new NoSuchElementException("No Repayment found for loan id: " + loanId));

        Integer repaymentId = repayment.getRepaymentId();

        Stream<LocalDateTime> successDates = repaymentSuccessRepository
                .findRepaymentSuccessByRepaymentId(repaymentId)
                .stream()
                .map(RepaymentSuccess::getPaymentDate);

        Stream<LocalDateTime> failDates = repaymentFailRepository
                .findRepaymentFailByRepaymentId(repaymentId)
                .stream()
                .map(RepaymentFail::getPaymentDate);

        return Stream.concat(successDates, failDates)
                .max(Comparator.naturalOrder()) // 최신 날짜 찾기
                .orElseThrow(() -> new NoSuchElementException("No payment dates found for loanId: " + loanId));
    }

    public Integer getLatestRepaymentTimesByLoanId(Integer loanId) {
        Repayment repayment = repaymentRepository.findByLoanId(loanId)
                .orElseThrow(() -> new NoSuchElementException("No Repayment found for loan id: " + loanId));

        Integer repaymentId = repayment.getRepaymentId();

        Stream<Integer> successRepaymentTimeses = repaymentSuccessRepository
                .findRepaymentSuccessByRepaymentId(repaymentId)
                .stream()
                .map(RepaymentSuccess::getRepaymentTimes);

        Stream<Integer> failRepaymentTimeses = repaymentFailRepository
                .findRepaymentFailByRepaymentId(repaymentId)
                .stream()
                .map(RepaymentFail::getRepaymentTimes);

        return Stream.concat(successRepaymentTimeses, failRepaymentTimeses)
                .max(Comparator.naturalOrder()) // 최신 날짜 찾기
                .orElseThrow(() -> new NoSuchElementException("No repayment times found for loanId: " + loanId));
    }

    // 여기서 depositSettlementAmount 호출해야 함!!
    public void createRepaymentProcess(RepaymentProcessCreateRequest request) {
        Repayment repayment = repaymentRepository.findByLoanId(request.getLoanId())
                .orElseThrow(() -> new NoSuchElementException("No Repayment found for loan id: " + request.getLoanId()));

        BigDecimal expectedTotal = repayment.getRepaymentPrincipal()
                .add(repayment.getRepaymentInterest());

        BigDecimal remainingAmount = request.getActualRepaymentAmount().subtract(expectedTotal);

        if (remainingAmount.compareTo(BigDecimal.ZERO) >= 0) {
            RepaymentSuccess success = new RepaymentSuccess();
            success.setRepaymentId(repayment.getRepaymentId());
            success.setPaymentDate(LocalDateTime.now());
            success.setRepaymentTimes(getLatestRepaymentTimesByLoanId(repayment.getLoanId())+1);
            repaymentSuccessRepository.save(success);

        } else {
            RepaymentFail fail = new RepaymentFail();
            fail.setRepaymentId(repayment.getRepaymentId());
            fail.setPaymentDate(LocalDateTime.now());
            fail.setRepaymentLeft(remainingAmount.abs());
            fail.setRepaymentTimes(getLatestRepaymentTimesByLoanId(repayment.getLoanId())+1);
            repaymentFailRepository.save(fail);
        }
    }
}