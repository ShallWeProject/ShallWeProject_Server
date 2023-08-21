package com.shallwe.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SignUpReq {

    @Schema( type = "string", example = "123123", description="카카오 고유 유저 ID 입니다.")
    private String providerId;

    @Schema( type = "string", example = "string", description="카카오톡 닉네임 입니다.")
    private String nickname;

    @Schema( type = "string", example = "string@aa.bb", description="계정 이메일 입니다.")
    @Email
    private String email;

    @Schema( type = "string", example = "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg", description="프로필 사진 URL 입니다.")
    private String profileImgUrl;

}
