package com.shallwe.domain.reservation.exception;

public class DefaultNullPointerException extends RuntimeException{
    public DefaultNullPointerException() {
        super("Null Point 에러가 발생했습니다.");
    }

}
