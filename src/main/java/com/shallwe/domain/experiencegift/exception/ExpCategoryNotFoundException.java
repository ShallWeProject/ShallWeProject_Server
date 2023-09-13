package com.shallwe.domain.experiencegift.exception;

public class ExpCategoryNotFoundException extends RuntimeException{
    public ExpCategoryNotFoundException(){
        super("경험 카테고리가 올바르지 않습니다.");
    }
}
