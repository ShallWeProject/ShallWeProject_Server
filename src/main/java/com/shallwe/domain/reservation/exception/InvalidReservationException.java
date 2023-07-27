package com.shallwe.domain.reservation.exception;

public class InvalidReservationException extends RuntimeException{
    public InvalidReservationException(){
        super("예약이 올바르지 않습니다.");
    }
}
