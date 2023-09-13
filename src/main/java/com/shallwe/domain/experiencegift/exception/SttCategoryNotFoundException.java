package com.shallwe.domain.experiencegift.exception;

public class SttCategoryNotFoundException extends RuntimeException{
    public SttCategoryNotFoundException (){
        super("상황 카테고리가 올바르지 않습니다.");
    }
}
