package com.shallwe.domain.experience_gift.exception;

public class SubtitleNotFoundException extends RuntimeException{
    public SubtitleNotFoundException(){
        super("부제목이 존재하지 않습니다.");
    }
}
