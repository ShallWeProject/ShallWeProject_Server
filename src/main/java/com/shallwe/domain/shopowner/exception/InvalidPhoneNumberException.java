package com.shallwe.domain.shopowner.exception;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException() {
        super("유효하지 않는 전화번호입니다.");
    }
}
