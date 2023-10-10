package com.shallwe.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ShopOwnerSignUpReq {

    @Schema( type = "string", example = "박세진", description="사장님 이름입니다.")
    private String name;

    @Schema( type = "string", example = "01012345678", description="전화번호입니다. 인증완료 성공했을 때 보내주세요.")
    private String phoneNumber;

    @Schema( type = "string", example = "asdfqwer1234!@#$", description="비밀번호입니다.")
    private String password;

}
