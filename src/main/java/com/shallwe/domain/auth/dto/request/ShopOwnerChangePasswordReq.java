package com.shallwe.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ShopOwnerChangePasswordReq {

    @Schema( type = "string", example = "01012345678" , description="사장의 핸드폰 번호입니다.")
    private String phoneNumber;

    @Schema( type = "string", example = "asdfqwer1234!@#$" , description="사장의 변경할 비밀번호입니다.")
    private String changePassword;

}
