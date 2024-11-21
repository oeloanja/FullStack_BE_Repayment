package com.billit.repayment.repository;

import com.billit.repayment.domain.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepaymentRepository extends JpaRepository<Repayment, Integer> {
    Optional<Repayment> findByLoanId(Integer loanId);
}


