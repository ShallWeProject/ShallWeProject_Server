package com.shallwe.domain.auth.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AppleSignUpReq {

    @Schema(type = "String", description = "애플 로그인을 위한 IdentityToken")
    private String identityToken;

    @Schema(type = "String", description = "애플 로그인을 위한 AuthorizationCode")
    private String authorizationCode;

    @Schema( type = "string", example = "string", description="사용자 이름 입니다.")
    private String name;

    @Schema( type = "string", example = "string@aa.bb", description="계정 이메일 입니다.")
    @Email
    private String email;

}