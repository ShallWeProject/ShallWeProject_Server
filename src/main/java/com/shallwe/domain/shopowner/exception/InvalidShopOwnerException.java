package com.shallwe.domain.shopowner.exception;

public class InvalidShopOwnerException extends RuntimeException {
    public InvalidShopOwnerException() {
        super("사장의 정보가 유효하지 않습니다.");
    }
}
