package com.shallwe.domain.auth.exception;

public class InvalidPhoneNumberException extends RuntimeException{

    public InvalidPhoneNumberException() {
        super("인증번호를 다시 전송해주세요");
    }

}
