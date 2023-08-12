package com.shallwe.domain.experience_gift.exception;

public class SttCategoryNotFoundException extends RuntimeException{
    public SttCategoryNotFoundException (){
        super("상황 카테고리가 올바르지 않습니다.");
    }
}
