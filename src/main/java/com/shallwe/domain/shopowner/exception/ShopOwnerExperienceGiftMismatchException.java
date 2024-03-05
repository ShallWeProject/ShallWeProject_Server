package com.shallwe.domain.shopowner.exception;

public class ShopOwnerExperienceGiftMismatchException extends RuntimeException {

    public ShopOwnerExperienceGiftMismatchException() {
        super("해당 사장의 상품이 아닙니다.");
    }

}
