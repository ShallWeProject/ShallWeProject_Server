package com.shallwe.domain.auth.exception;

public class UnRegisteredUserException extends RuntimeException {

    public UnRegisteredUserException() {
        super("회원가입이 완료되지 않은 유저입니다.");
    }

}
