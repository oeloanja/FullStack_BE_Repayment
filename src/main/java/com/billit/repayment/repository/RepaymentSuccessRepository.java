package com.billit.repayment.repository;

import com.billit.repayment.domain.RepaymentFail;
import com.billit.repayment.domain.RepaymentSuccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepaymentSuccessRepository extends JpaRepository<RepaymentSuccess, Long> {
    List<RepaymentSuccess> findRepaymentSuccessByRepaymentId(Integer repaymentId);
}

