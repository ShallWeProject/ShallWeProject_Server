package com.shallwe.domain.auth.dto.request;

import com.shallwe.domain.user.domain.Provider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignInReq {

    @Schema(type = "String", example = "KAKAO / GOOGLE", description = "Provider")
    private Provider provider;

    @Schema(type = "String", description = "ProviderId")
    private String providerId;

    @Schema(type = "String", description = "Email")
    private String email;

    @Schema(type = "String", description = "Google 자체에서 제공해주는 Refresh Token / 카카오는 null로 보내주세요.")
    private String refreshToken;

}
