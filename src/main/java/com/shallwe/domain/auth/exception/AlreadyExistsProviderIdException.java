package com.shallwe.domain.auth.exception;

public class AlreadyExistsProviderIdException extends RuntimeException {

    public AlreadyExistsProviderIdException() {
        super("이미 존재하는 providerId 입니다.");
    }

}
