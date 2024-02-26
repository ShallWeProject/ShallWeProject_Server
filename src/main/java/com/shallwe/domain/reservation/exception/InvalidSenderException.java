package com.shallwe.domain.reservation.exception;

public class InvalidSenderException extends RuntimeException{

    public InvalidSenderException(){
        super("발송자 정보가 올바르지 않습니다.");
    }

}
