package com.shallwe.domain.user.exception;

public class DefaultException extends RuntimeException {
    public DefaultException(){
        super("유저가 올바르지 않습니다.");
    }
}
