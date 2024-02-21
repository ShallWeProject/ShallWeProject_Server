package com.shallwe.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AppleSignInReq {

    @Schema(type = "string", description = "애플 로그인을 위한 IdentityToken")
    private String identityToken;

}
