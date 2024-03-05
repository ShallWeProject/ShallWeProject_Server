package com.shallwe.domain.shopowner.exception;

public class NotReservedException extends RuntimeException {

        public NotReservedException() {
            super("예약 확정 대기상태가 아닌 예약입니다.");
        }

}
