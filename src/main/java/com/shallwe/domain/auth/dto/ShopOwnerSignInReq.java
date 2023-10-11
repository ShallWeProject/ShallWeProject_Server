package com.shallwe.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ShopOwnerSignInReq {

    @Schema( type = "string", example = "01012345678", description="사장님 핸드폰 번호입니다.")
    private String phoneNumber;

    @Schema( type = "string", example = "qwer1234!@#$", description="사장님 비밀번호입니다.")
    private String password;

}
