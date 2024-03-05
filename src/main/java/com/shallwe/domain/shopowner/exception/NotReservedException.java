package com.shallwe.domain.shopowner.exception;

public class NotReservedException extends RuntimeException {

        public NotReservedException() {
            super("예약 내역이 없습니다.");
        }

}
