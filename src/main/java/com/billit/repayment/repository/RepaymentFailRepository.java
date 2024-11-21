package com.billit.repayment.repository;

import com.billit.repayment.domain.RepaymentFail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepaymentFailRepository extends JpaRepository<RepaymentFail, Long> {
    List<RepaymentFail> findRepaymentFailByRepaymentId(Integer repaymentId);
}

