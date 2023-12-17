package com.shallwe.domain.shopowner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ShopOwnerIdentificationReq {

    @Schema(type = "string", example = "uploads/image.png", description = " 사장님 주민등록번호 사진 키")
    private String identification;

    @Schema(type = "string", example = "uploads/image.png", description = " 사장님 사업자등록증 사진 키")
    private String businessRegistration;

    @Schema(type = "string", example = "uploads/image.png", description = " 사장님 통장사본 사진 키")
    private String bankbook;

}
