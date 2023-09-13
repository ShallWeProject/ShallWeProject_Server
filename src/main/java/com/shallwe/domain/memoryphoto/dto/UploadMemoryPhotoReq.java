package com.shallwe.domain.memoryphoto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UploadMemoryPhotoReq {

    @Schema(type = "string", example = "uploads/01231564.jpg", description = "메모리포토 이미지 Key 입니다.")
    private String memoryPhotoImgKey;

    @Schema(type = "Integer", example = "1", description = "예약 ID 입니다.")
    private Long reservationId;

}
