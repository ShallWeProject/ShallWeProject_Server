package com.shallwe.domain.shopowner.exception;

public class AlreadyExistPhoneNumberException extends RuntimeException {
    public AlreadyExistPhoneNumberException() {
        super("이미 존재하는 전화번호입니다.");
    }
}
