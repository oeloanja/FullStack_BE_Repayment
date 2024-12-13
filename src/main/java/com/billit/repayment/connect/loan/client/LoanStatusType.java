package com.billit.repayment.connect.loan.client;

import lombok.Getter;

@Getter
public enum LoanStatusType {
    WAITING("대출 희망", "대출 신청이 완료된 상태"),
    EXECUTING("실행 중", "대출금이 지급되어 상환 중인 상태"),
    COMPLETED("상환 완료", "대출금을 전액 상환한 상태"),
    OVERDUE("연체", "최종 상환일 까지 상환이 완료되지 않은 상태"),
    REJECTED("거절", "대출을 신청 하였으나, 거절 된 상태"),
    CANCELED("취소됨","대출 신청을 하였으나 사용자가 취소함");

    private final String description;
    private final String detail;

    LoanStatusType(String description, String detail) {
        this.description = description;
        this.detail = detail;
    }
}
