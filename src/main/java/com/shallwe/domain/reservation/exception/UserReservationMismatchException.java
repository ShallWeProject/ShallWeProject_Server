package com.shallwe.domain.reservation.exception;

public class UserReservationMismatchException extends RuntimeException {

    public UserReservationMismatchException() {
        super("예약자 정보가 일치하지 않습니다.");
    }

}
