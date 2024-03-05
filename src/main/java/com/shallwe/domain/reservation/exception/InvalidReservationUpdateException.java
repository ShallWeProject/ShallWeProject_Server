package com.shallwe.domain.reservation.exception;

public class InvalidReservationUpdateException extends RuntimeException{

  public InvalidReservationUpdateException() {
    super("변경이 불가능한 예약 시간입니다.");
  }
}
