package com.shallwe.domain.auth.dto.request;

import com.shallwe.domain.user.domain.Provider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SignUpReq {

    @Schema( type = "string", example = "123123", description="카카오/애플 고유 유저 ID 입니다.")
    private String providerId;

    @Schema( type = "string", example = "KAKAO / GOOGLE ", description="카카오/애플/구글 로그인 제공자 입니다.")
    private Provider provider;

    @Schema( type = "string", example = "string", description="사용자 이름 입니다.")
    private String name;

    @Schema( type = "string", example = "string@aa.bb", description="계정 이메일 입니다.")
    @Email
    private String email;

}
