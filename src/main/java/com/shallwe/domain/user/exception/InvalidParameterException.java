package com.shallwe.domain.user.exception;

public class InvalidParameterException extends RuntimeException{
    public InvalidParameterException() {
        super("잘못된 매개변수를 입력했습니다.");
    }


}
