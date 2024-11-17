package com.billit.repayment.repository;

import com.billit.repayment.domain.RepaymentFail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepaymentFailRepository extends JpaRepository<RepaymentFail, Long> {
}

