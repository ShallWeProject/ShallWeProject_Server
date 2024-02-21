package com.shallwe.domain.auth.exception;

public class InvalidProviderIdException extends RuntimeException {

    public InvalidProviderIdException() {
        super("존재하지 않는 provider id 입니다.");
    }

}
