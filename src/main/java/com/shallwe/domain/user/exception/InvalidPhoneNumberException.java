package com.shallwe.domain.user.exception;

public class InvalidPhoneNumberException extends RuntimeException {

    public InvalidPhoneNumberException() {
        super("해당 전화번호의 유저가 없습니다.");
    }

}
