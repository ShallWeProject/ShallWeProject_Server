package com.shallwe.domain.reservation.exception;

public class InvalidUserException extends RuntimeException{

    public InvalidUserException(){
        super("유저 정보가 올바르지 않습니다.");
    }

}
