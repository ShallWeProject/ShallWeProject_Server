package com.shallwe.domain.user.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("이미 삭제된 토큰입니다.");
    }

}
