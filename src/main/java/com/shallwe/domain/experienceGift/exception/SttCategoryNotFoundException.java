package com.shallwe.domain.experienceGift.exception;

public class SttCategoryNotFoundException extends RuntimeException{
    public SttCategoryNotFoundException (){
        super("상황 카테고리가 올바르지 않습니다.");
    }
}
