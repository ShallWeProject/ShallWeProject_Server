package com.shallwe.domain.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationStatus {
    //본인 or 관리자가 취소한 경우(수동)
    CANCELLED("에약취소"),
    //유저가 예약 등록한 경우 (미결제)
    BOOKED("예약중"),
    //사장님이 예약을 확정한 경우(결제완료, 수동)
    CONFIRMED("예약확정"),
    //예약 일시가 끝난 경우(자동)
    COMPLETED("이용완료"),
    //회원이 예약한 경우(자동)
    WAITING("예약대기"),
    //예약일시인 경우(자동) but 예약 승인하지 않은 경우에는 예약 대기
    USING("이용중");

    private final String value;


}
