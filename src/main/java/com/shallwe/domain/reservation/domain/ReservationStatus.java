package com.shallwe.domain.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationStatus {
    //본인 or 관리자가 취소한 경우(수동)
    CANCELLED("에약취소"),
    //관리자가 예약 승인한 경우(수동)
    BOOKED("예약중"),
    //예약 일시가 끝난 경우(자동)
    COMPLETED("이용완료"),
    //회원이 예약한 경우(자동)
    WAITING("예약대기"),
    //예약일시인 경우(자동) but 예약 승인하지 않은 경우에는 예약 대기
    USING("이용중");

    private final String value;


}
