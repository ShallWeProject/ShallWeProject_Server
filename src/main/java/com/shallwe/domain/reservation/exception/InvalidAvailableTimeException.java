package com.shallwe.domain.reservation.exception;

public class InvalidAvailableTimeException extends RuntimeException{

  public InvalidAvailableTimeException(){super("가능한 시간이 없습니다.");}
}
