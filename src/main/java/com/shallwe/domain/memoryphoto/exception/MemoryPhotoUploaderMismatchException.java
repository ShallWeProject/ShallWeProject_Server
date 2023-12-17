package com.shallwe.domain.memoryphoto.exception;

public class MemoryPhotoUploaderMismatchException extends RuntimeException {

    public MemoryPhotoUploaderMismatchException() {
        super("사진을 업로드한 사용자가 아닙니다.");
    }

}
