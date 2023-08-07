package com.shallwe.domain.reservation.exception;

public class InvalidEntityException extends RuntimeException {

    public InvalidEntityException(){super("잘못된 엔티티 삽입입니다. ");}

}
