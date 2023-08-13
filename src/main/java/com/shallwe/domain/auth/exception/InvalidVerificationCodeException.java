package com.shallwe.domain.auth.exception;

public class InvalidVerificationCodeException extends RuntimeException {

    public InvalidVerificationCodeException() {
        super("인증번호가 일치하지 않습니다.");
    }

}
