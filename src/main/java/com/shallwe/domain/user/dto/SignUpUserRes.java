package com.shallwe.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SignUpUserRes {

    private String message;

    @Builder
    public SignUpUserRes(String message) {
        this.message = message;
    }

    public static SignUpUserRes toDto() {
        SignUpUserResBuilder builder = SignUpUserRes.builder()
                .message("회원가입 되었습니다");
        return builder.build();
    }

}
