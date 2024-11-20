package com.billit.repayment.repository;

import com.billit.repayment.domain.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepaymentRepository extends JpaRepository<Repayment, Integer> {
    List<Repayment> findByLoanId(Integer loanId);
    List<Repayment> findByInvestmentId(Integer investmentId);
    Optional<Repayment> findByLoanIdAndInvestmentId(Integer loanId, Integer investmentId);
}


