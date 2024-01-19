package com.shallwe.global.infrastructure.sms.exception;

public class TimeOutException extends RuntimeException{

    public TimeOutException() {
        super("시간초과 되었습니다.");
    }

}
