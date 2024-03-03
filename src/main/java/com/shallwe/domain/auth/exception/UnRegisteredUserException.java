package com.shallwe.domain.auth.exception;

public class UnRegisteredUserException extends RuntimeException {

    public UnRegisteredUserException() {
        super("유저정보 입력이 완료되지 않은 유저입니다.");
    }

}
