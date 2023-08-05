package com.shallwe.global.util;

import org.springframework.util.StringUtils;

import java.util.UUID;

public final class MultipartUtil {
    private static final String BASE_DIR = "images";
    //로컬에서 사용자 홈 디렉토리 경로 반환
    public static String getLocalHomedirectory(){
        return System.getProperty("user.home");
    }

    //새로운 파일 고유 ID 생성
    //@return 36자리 UUID
    public static String CreateFileId(){
        return UUID.randomUUID().toString();
    }
    //Multipart의 ContentType 값에서 / 이후 확장자만 잘라냅니다.
    public static String getFormat(String contentType){
        if(StringUtils.hasText(contentType)){
            return contentType.substring(contentType.lastIndexOf('/')+1);
        }
        return null;
    }
    //파일의 전체 경로를 생성합니다.
    public static String createPath(String fileId, String format){
        return String.format(("%s/%s.%s"),BASE_DIR, fileId,format);
    }
}
