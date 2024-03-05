package com.shallwe.domain.reservation.exception;

public class NotAvailableReservationStatusException extends RuntimeException {

    public NotAvailableReservationStatusException() {
        super("예약 취소 가능한 상태가 아닙니다.");
    }

}
