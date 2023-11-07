package com.shallwe.domain.experiencegift.exception;

public class ChooseOnlyOneCategory extends RuntimeException{
    public ChooseOnlyOneCategory(){super("경험 카테고리와 상황 카테고리 둘 중 하나만 선택해주세요.");};
}
