package com.shallwe.domain.reservation.exception;

public class InvalidReceiverException extends RuntimeException{

 public InvalidReceiverException(){super("수신자 정보가 올바르지 않습니다.");}

}
