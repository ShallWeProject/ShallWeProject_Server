package com.shallwe.domain.reservation.exception;

public class DefaultException extends RuntimeException{
    public DefaultException(){
        super("예약이 올바르지 않습니다.");
    }
}
