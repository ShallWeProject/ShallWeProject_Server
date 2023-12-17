package com.shallwe.domain.memoryphoto.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemoryPhotoUserDetailRes {

    private Boolean isUploader;

    private String memoryPhotoImgUrl;

    public static MemoryPhotoUserDetailRes toDto(String memoryPhotoImgUrl, Boolean isUploader) {
        return MemoryPhotoUserDetailRes.builder()
                .memoryPhotoImgUrl(memoryPhotoImgUrl)
                .isUploader(isUploader)
                .build();
    }

}
