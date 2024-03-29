package com.shallwe.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AppleSignInReq {

    @Schema(type = "String", description = "애플 로그인을 위한 ProviderId")
    private String providerId;

    @Schema(type = "String", description = "애플 로그인을 위한 IdentityToken")
    private String identityToken;

    @Schema(type = "String", description = "애플 로그인을 위한 AuthorizationCode")
    private String authorizationCode;

}