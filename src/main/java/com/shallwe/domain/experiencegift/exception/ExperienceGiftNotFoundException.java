package com.shallwe.domain.experiencegift.exception;

public class ExperienceGiftNotFoundException extends RuntimeException{

    public ExperienceGiftNotFoundException(){super("요청한 경험을 찾을 수 없습니다");}

}
