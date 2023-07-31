package com.shallwe.domain.experience_gift.exception;

public class ExperienceGiftNotFoundException extends RuntimeException{
    public ExperienceGiftNotFoundException(){super("요청한 경험을 찾을 수 없습니다");}
}
